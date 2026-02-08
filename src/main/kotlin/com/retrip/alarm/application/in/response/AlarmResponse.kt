package com.retrip.alarm.application.in.response

import com.retrip.alarm.domain.entity.Alarm
import com.retrip.alarm.domain.vo.AlarmType
import java.time.LocalDateTime

data class AlarmResponse(
    val id: Long,
    val senderId: Long?,
    val tripId: Long,
    val title: String,
    val body: String,
    val type: AlarmType,
    val isRead: Boolean,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(alarm: Alarm): AlarmResponse {
            return AlarmResponse(
                id = alarm.id ?: 0,
                senderId = alarm.senderId,
                tripId = alarm.tripId,
                title = alarm.title,
                body = alarm.body,
                type = alarm.type,
                isRead = alarm.isRead,
                createdAt = alarm.createdAt
            )
        }
    }
}
