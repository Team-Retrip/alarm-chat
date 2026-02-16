package com.retrip.alarm.application.out.repository

import com.retrip.alarm.application.`in`.response.AlarmMemberResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.UUID

interface AlarmMemberQueryRepository {
    fun findAlarmMembers(id: UUID?, page: Pageable) : Page<AlarmMemberResponse>
}
