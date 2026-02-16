package com.retrip.alarm.application.out.repository

import com.retrip.alarm.application.`in`.response.AlarmResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.UUID

interface AlarmQueryRepository {
    fun findAlarmsByRecipientId(recipientId: UUID, page: Pageable): Page<AlarmResponse>
    fun countAlarmCountByUnRead(recipientId: UUID): Long
}
