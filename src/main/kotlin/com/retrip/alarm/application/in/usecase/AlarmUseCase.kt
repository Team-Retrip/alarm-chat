package com.retrip.alarm.application.`in`.usecase

import com.retrip.alarm.application.`in`.request.CreateAlarmRequest
import com.retrip.alarm.application.`in`.response.AlarmResponse
import com.retrip.alarm.application.`in`.response.CreateAlarmsResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.UUID

interface AlarmUseCase {
    fun createAlarm(request: CreateAlarmRequest): CreateAlarmsResponse
    fun getAlarms(receiverId: UUID, page: Pageable): Page<AlarmResponse>
    fun getUnreadCount(receiverId: UUID): Long
    fun readAlarm(alarmId: UUID, receiverId: UUID)
    fun readAll(receiverId: UUID)
    fun deleteOldAlarms()
}
