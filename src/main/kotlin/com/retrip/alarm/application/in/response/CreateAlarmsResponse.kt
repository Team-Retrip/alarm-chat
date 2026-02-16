package com.retrip.alarm.application.`in`.response

import com.retrip.alarm.domain.entity.Alarm
import com.retrip.alarm.domain.vo.AlarmType
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.*

@Schema(description = "알림 생성 응답")
data class CreateAlarmsResponse(
    val alarms: List<CreateAlarmResponse>?
) {
    data class CreateAlarmResponse(
        val id: UUID,
        val senderId: UUID?,
        val receiverId: UUID?,
        val title: String,
        val body: String,
        val type: AlarmType,
        val isRead: Boolean,
        val createdAt: LocalDateTime
    ) {
        companion object {
            fun from(alarm: Alarm): CreateAlarmResponse {
                return CreateAlarmResponse(
                    id = alarm.id,
                    senderId = alarm.senderId,
                    receiverId = alarm.receiverId,
                    title = alarm.title,
                    body = alarm.body,
                    type = alarm.type,
                    isRead = alarm.isRead,
                    createdAt = alarm.createdAt
                )
            }
        }
    }

    companion object {
        fun from(alarms: List<Alarm>): CreateAlarmsResponse {
            return CreateAlarmsResponse(
                alarms = alarms.map {
                    CreateAlarmResponse.from(it)
                }
            )
        }
    }
}


