package com.retrip.alarm.application.in.usecase

import com.retrip.alarm.domain.entity.Alarm
import com.retrip.alarm.domain.vo.AlarmType

interface AlarmUseCase {
    fun createAlarm(
        recipientId: Long,
        senderId: Long?,
        senderName: String?,
        tripId: Long,
        tripTitle: String,
        type: AlarmType,
        token: String?
    ): Alarm

    fun getAlarms(recipientId: Long, page: Int, size: Int): List<Alarm>
    fun getUnreadCount(recipientId: Long): Long
    fun readAlarm(alarmId: Long, recipientId: Long)
    fun readAll(recipientId: Long)
    fun deleteOldAlarms()
}
