package com.retrip.alarm.application.`in`.response

import com.retrip.alarm.domain.entity.AlarmMember
import java.util.*


data class UpdateAlarmMemberResponse(
    val id: UUID,
) {
    companion object {
        fun from(alarm: AlarmMember): UpdateAlarmMemberResponse {
            return UpdateAlarmMemberResponse(
                id = alarm.id,
            )
        }
    }
}
