package com.retrip.alarm.application.`in`.request

import com.retrip.alarm.domain.entity.Alarm
import com.retrip.alarm.domain.vo.AlarmType
import java.util.UUID


data class CreateAlarmRequest(
    val senderId: UUID?,
    val receiverIds: List<UUID>,
    val parameters: Map<String, Any>?,
    val type: AlarmType
) {
    fun toAlarm(title: String, body: String, receiverId: UUID): Alarm {
        return Alarm(
            id = UUID.randomUUID(),
            senderId = senderId,
            receiverId = receiverId,
            title = title,
            body = body,
            type = type,
        )
    }
}
