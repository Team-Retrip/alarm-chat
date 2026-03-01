package com.retrip.alarm.application.`in`.response

import com.retrip.alarm.domain.entity.AlarmMember
import java.util.*

data class CreateAlarmMemberResponse(
    val id: UUID,
) {
    companion object {
        fun from(alarm: AlarmMember): CreateAlarmMemberResponse {
            return CreateAlarmMemberResponse(
                id = alarm.id,
            )
        }
    }
}
