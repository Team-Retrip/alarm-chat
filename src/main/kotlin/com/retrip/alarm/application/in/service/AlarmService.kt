package com.retrip.alarm.application.`in`.service

import com.retrip.alarm.application.`in`.usecase.AlarmUseCase
import com.retrip.alarm.application.out.external.PushService
import com.retrip.alarm.application.out.repository.AlarmRepository
import com.retrip.alarm.domain.entity.Alarm
import com.retrip.alarm.domain.vo.AlarmType
import com.retrip.alarm.domain.exception.common.BusinessException
import com.retrip.alarm.domain.exception.common.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class AlarmService(
    private val alarmRepository: AlarmRepository,
    private val pushService: PushService
) : AlarmUseCase {

    override fun createAlarm(
        recipientId: Long,
        senderId: Long?,
        senderName: String?,
        tripId: Long,
        tripTitle: String,
        type: AlarmType,
        token: String?
    ): Alarm {
        
        // 1. Generate Message from Template
        val title = type.createTitle()
        val body = type.createBody(senderName, tripTitle)

        // 2. Save Alarm to DB
        val alarm = Alarm(
            recipientId = recipientId,
            senderId = senderId,
            tripId = tripId,
            title = title,
            body = body,
            type = type
        )
        val savedAlarm = alarmRepository.save(alarm)

        // 3. Send Push Notification
        if (token != null) {
            pushService.sendPush(
                token = token,
                title = title,
                body = body,
                data = mapOf(
                    "alarmId" to savedAlarm.id.toString(),
                    "type" to type.name,
                    "tripId" to tripId.toString()
                )
            )
        }
        
        return savedAlarm
    }

    @Transactional(readOnly = true)
    override fun getAlarms(recipientId: Long, page: Int, size: Int): List<Alarm> {
        return alarmRepository.findAllByRecipientId(recipientId, page, size)
    }

    @Transactional(readOnly = true)
    override fun getUnreadCount(recipientId: Long): Long {
        return alarmRepository.countUnreadByRecipientId(recipientId)
    }

    override fun readAlarm(alarmId: Long, recipientId: Long) {
        val alarm = alarmRepository.findById(alarmId)
            ?: throw BusinessException(ErrorCode.ALARM_NOT_FOUND)
        
        if (alarm.recipientId != recipientId) {
             throw SecurityException("User \$recipientId is not the recipient of alarm \$alarmId")
        }

        alarm.read()
        alarmRepository.save(alarm)
    }

    override fun readAll(recipientId: Long) {
        alarmRepository.markAllAsRead(recipientId)
    }

    override fun deleteOldAlarms() {
        val retentionPeriod = LocalDateTime.now().minusDays(30)
        alarmRepository.deleteBefore(retentionPeriod)
    }
}
