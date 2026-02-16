package com.retrip.alarm.application.`in`.request

import com.retrip.alarm.domain.entity.Alarm
import com.retrip.alarm.domain.vo.AlarmType
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID


@Schema(description = "알림 생성")
data class CreateAlarmRequest(
    val senderId: UUID?,
    val senderName: String?,
    val receiverIds: List<UUID>,
    val parameters: List<String>?,
    @Schema(
        description = "알림 타입", allowableValues = ["INVITATION", "TRIP_CHANGE", "TRIP_CONFIRM", "KICK", "DELETE"]
    )
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
