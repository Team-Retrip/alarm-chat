package com.retrip.alarm.application.out.repository

import com.retrip.alarm.domain.entity.Alarm
import com.retrip.alarm.domain.entity.AlarmMember
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.util.UUID

interface AlarmMemberRepository: JpaRepository<AlarmMember, UUID> {
    fun findByMemberId(memberId: UUID): AlarmMember?
}
