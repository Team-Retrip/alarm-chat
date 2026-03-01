package com.retrip.alarm.application.`in`.service

import com.retrip.alarm.application.`in`.request.CreateAlarmRequest
import com.retrip.alarm.application.`in`.response.AlarmResponse
import com.retrip.alarm.application.`in`.response.CreateAlarmsResponse
import com.retrip.alarm.application.`in`.usecase.AlarmUseCase
import com.retrip.alarm.application.out.external.PushPort
import com.retrip.alarm.application.out.repository.AlarmMemberRepository
import com.retrip.alarm.application.out.repository.AlarmQueryRepository
import com.retrip.alarm.application.out.repository.AlarmRepository
import com.retrip.alarm.application.out.request.PushRequest
import com.retrip.alarm.domain.exception.common.BusinessException
import com.retrip.alarm.domain.exception.common.ErrorCode
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional
class AlarmService(
    private val alarmRepository: AlarmRepository,
    private val alarmQueryRepository: AlarmQueryRepository,
    private val alarmMemberRepository: AlarmMemberRepository,
    private val pushPort: PushPort
) : AlarmUseCase {

    override fun createAlarm(request: CreateAlarmRequest): CreateAlarmsResponse {
        // 1. Generate Message from Template
        val title = request.type.createTitle()
        val body = request.type.createBody(request.parameters)

        // 2. Save Alarm to DB
        val alarmAndAlarmMembers = request.receiverIds.mapNotNull {
            val alarmMember =
                alarmMemberRepository.findByMemberId(it) ?: return@mapNotNull null
            val alarm = alarmRepository.save(
                request.toAlarm(
                    title,
                    body,
                    it
                )
            )
            alarm to alarmMember
        }

        val pushRequests = alarmAndAlarmMembers.map { (alarm, member) ->
            PushRequest(
                token = member.fcmToken,
                title = title,
                body = body,
                data = mapOf(
                    "alarmId" to alarm.id.toString(),
                    "type" to alarm.type.name,
                )
            )
        }
        pushPort.sendPush(pushRequests)

        return CreateAlarmsResponse.from(alarmAndAlarmMembers.map { it.first })
    }

    @Transactional(readOnly = true)
    override fun getAlarms(receiverId: UUID, page: Pageable): Page<AlarmResponse> {
        return alarmQueryRepository.findAlarmsByRecipientId(receiverId, page)
    }

    @Transactional(readOnly = true)
    override fun getUnreadCount(receiverId: UUID): Long {
        return alarmQueryRepository.countAlarmCountByUnRead(receiverId)
    }

    override fun readAlarm(alarmId: UUID, receiverId: UUID) {
        val alarm = alarmRepository.findById(alarmId).orElseThrow { throw BusinessException(ErrorCode.ALARM_NOT_FOUND) }
        if (alarm.receiverId != receiverId) {
            throw BusinessException(ErrorCode.ALARM_CAN_NOT_RECIPIENT)
        }
        alarm.read()
    }

    override fun readAll(receiverId: UUID) {
        alarmRepository.markAllAsRead(receiverId)
    }

    override fun deleteOldAlarms() {
        val retentionPeriod = LocalDateTime.now().minusDays(30)
        alarmRepository.deleteBefore(retentionPeriod)
    }
}
