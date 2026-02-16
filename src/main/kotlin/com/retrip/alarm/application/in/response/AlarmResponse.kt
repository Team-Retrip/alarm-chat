package com.retrip.alarm.application.`in`.response

import com.retrip.alarm.domain.vo.AlarmType
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.*

@Schema(description = "알림")
data class AlarmResponse(
    val id: UUID,
    val senderId: UUID?,
    val receiverId: UUID?,
    val title: String,
    val body: String,
    @Schema(
        description = "알림 타입", allowableValues = ["INVITATION", "TRIP_CHANGE", "TRIP_CONFIRM", "KICK", "DELETE"]
    )
    val type: AlarmType,
    val isRead: Boolean,
    val createdAt: LocalDateTime
)


