package com.retrip.alarm.application.`in`.response

import java.util.*

data class AlarmMemberResponse(
    val id: UUID,
    val memberId: UUID,
    val fcmToken: String,
) {
}
