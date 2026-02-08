package com.retrip.alarm.infra.adapter.out.persistence

import com.retrip.alarm.application.out.repository.AlarmRepository
import com.retrip.alarm.domain.entity.Alarm
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class AlarmRepositoryImpl(
    private val alarmJpaRepository: AlarmJpaRepository
) : AlarmRepository {

    override fun save(alarm: Alarm): Alarm {
        return alarmJpaRepository.save(alarm)
    }

    override fun findById(id: Long): Alarm? {
        return alarmJpaRepository.findById(id).orElse(null)
    }

    override fun findAllByRecipientId(recipientId: Long, page: Int, size: Int): List<Alarm> {
        val pageable = PageRequest.of(page, size)
        return alarmJpaRepository.findAllByRecipientIdOrderByCreatedAtDesc(recipientId, pageable)
    }

    override fun countUnreadByRecipientId(recipientId: Long): Long {
        return alarmJpaRepository.countByRecipientIdAndIsReadFalse(recipientId)
    }

    override fun markAllAsRead(recipientId: Long) {
        alarmJpaRepository.markAllAsRead(recipientId)
    }

    override fun deleteBefore(dateTime: LocalDateTime) {
        alarmJpaRepository.deleteBefore(dateTime)
    }

    override fun deleteAllByTripId(tripId: Long) {
        alarmJpaRepository.deleteAllByTripId(tripId)
    }
}
