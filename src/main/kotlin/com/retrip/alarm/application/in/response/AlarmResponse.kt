package com.retrip.alarm.application.`in`.response

import com.retrip.alarm.domain.vo.AlarmType
import java.time.LocalDateTime
import java.util.*

data class AlarmResponse(
    val id: UUID,
    val senderId: UUID?,
    val receiverId: UUID?,
    val title: String,
    val body: String,
    val type: AlarmType,
    val isRead: Boolean,
    val createdAt: LocalDateTime
)


