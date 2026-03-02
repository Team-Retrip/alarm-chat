package com.retrip.alarm.application.`in`.usecase

import com.retrip.alarm.application.`in`.request.CreateAlarmMemberRequest
import com.retrip.alarm.application.`in`.request.UpdateAlarmMemberRequest
import com.retrip.alarm.application.`in`.response.AlarmMemberResponse
import com.retrip.alarm.application.`in`.response.CreateAlarmMemberResponse
import com.retrip.alarm.application.`in`.response.UpdateAlarmMemberResponse
import com.retrip.alarm.application.`in`.request.context.UserContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.UUID

interface AlarmMemberUseCase {
    fun createAlarmMember(context: UserContext, request: CreateAlarmMemberRequest): CreateAlarmMemberResponse
    fun getAlarmMembers(id: UUID?, page: Pageable): Page<AlarmMemberResponse>
    fun updateAlarmMember(id: UUID, context: UserContext, request: UpdateAlarmMemberRequest): UpdateAlarmMemberResponse
    fun deleteAlarmMember(id: UUID)
}
