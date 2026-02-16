package com.retrip.alarm.application.`in`.response

import com.retrip.alarm.domain.entity.AlarmMember
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "수정된 알림 맴버")
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
