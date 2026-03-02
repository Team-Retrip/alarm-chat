package com.retrip.alarm.application.`in`.request

import com.retrip.alarm.domain.entity.AlarmMember
import java.util.UUID

data class CreateAlarmMemberRequest(
    val fcmToken: String
) {
    fun toAlarmMember(memberId: UUID): AlarmMember {
        return AlarmMember(
            id = UUID.randomUUID(),
            memberId = memberId,
            fcmToken = fcmToken
        )
    }
}
