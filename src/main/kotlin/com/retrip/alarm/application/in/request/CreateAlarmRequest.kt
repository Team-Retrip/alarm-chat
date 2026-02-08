package com.retrip.alarm.application.`in`.request

import com.retrip.alarm.domain.vo.AlarmType

data class CreateAlarmRequest(
    val recipientId: Long,
    val senderId: Long?,
    val senderName: String?, // Added for template
    val tripId: Long,
    val tripTitle: String,   // Added for template
    val type: AlarmType,
    val fcmToken: String? // Optional
)
