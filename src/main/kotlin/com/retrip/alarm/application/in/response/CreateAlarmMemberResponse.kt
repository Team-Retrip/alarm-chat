package com.retrip.alarm.application.`in`.response

import com.retrip.alarm.domain.entity.Alarm
import com.retrip.alarm.domain.entity.AlarmMember
import com.retrip.alarm.domain.vo.AlarmType
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.UUID

@Schema(description = "알림 맴버 생성")
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
