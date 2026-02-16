package com.retrip.alarm.application.`in`.request

import com.retrip.alarm.domain.entity.AlarmMember
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema(description = "알림 맴버 생성")
data class CreateAlarmMemberRequest(
    val memberId: UUID,
    val fcmToken: String
) {
    fun toAlarmMember(): AlarmMember {
        return AlarmMember(
            id = UUID.randomUUID(),
            memberId = UUID.randomUUID(),
            fcmToken = fcmToken
        )
    }
}
