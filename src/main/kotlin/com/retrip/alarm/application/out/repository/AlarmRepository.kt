package com.retrip.alarm.application.out.repository

import com.retrip.alarm.domain.entity.Alarm
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.*

interface AlarmRepository : JpaRepository<Alarm, UUID> {
    @Modifying
    @Query("UPDATE Alarm a SET a.isRead = true WHERE a.receiverId = :receiverId AND a.isRead = false")
    fun markAllAsRead(receiverId: UUID)

    @Modifying
    @Query("DELETE FROM Alarm a WHERE a.createdAt < :dateTime")
    fun deleteBefore(dateTime: LocalDateTime)

}
