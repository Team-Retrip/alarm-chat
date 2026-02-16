package com.retrip.alarm.application.`in`.request

import java.util.*

data class UpdateAlarmMemberRequest(
    val memberId: UUID,
    val fcmToken: String
) {

}
