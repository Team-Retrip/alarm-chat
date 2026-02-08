package com.retrip.alarm.infra.adapter.out.persistence

import com.retrip.alarm.domain.entity.Alarm
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface AlarmJpaRepository : JpaRepository<Alarm, Long> {
    
    fun findAllByRecipientIdOrderByCreatedAtDesc(recipientId: Long, pageable: Pageable): List<Alarm>

    fun countByRecipientIdAndIsReadFalse(recipientId: Long): Long

    @Modifying
    @Query("UPDATE Alarm a SET a.isRead = true WHERE a.recipientId = :recipientId AND a.isRead = false")
    fun markAllAsRead(recipientId: Long)

    @Modifying
    @Query("DELETE FROM Alarm a WHERE a.createdAt < :dateTime")
    fun deleteBefore(dateTime: LocalDateTime)

    fun deleteAllByTripId(tripId: Long)
}
