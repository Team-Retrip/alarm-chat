package com.retrip.alarm.application.out.repository

import com.retrip.alarm.domain.entity.Alarm

interface AlarmRepository {
    fun save(alarm: Alarm): Alarm
    fun findById(id: Long): Alarm?
    fun findAllByRecipientId(recipientId: Long, page: Int, size: Int): List<Alarm>
    fun countUnreadByRecipientId(recipientId: Long): Long
    fun markAllAsRead(recipientId: Long)
    fun deleteBefore(dateTime: java.time.LocalDateTime)
    fun deleteAllByTripId(tripId: Long)
}
