package com.retrip.alarm.application.`in`.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "않읽은 알림")
data class UnreadCountResponse(
    val count: Long
)
