package com.retrip.alarm.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import com.retrip.alarm.domain.vo.AlarmType

@Entity
class Alarm(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val recipientId: Long, // 알림 받는 사람

    @Column(nullable = true)
    val senderId: Long? = null, // 알림 보낸 사람 (시스템 알림일 경우 null 가능)

    @Column(nullable = false)
    val tripId: Long, // 관련된 여행 ID

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val body: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: AlarmType,

    @Column(nullable = false)
    var isRead: Boolean = false,
) : BaseEntity() {
    fun read() {
        this.isRead = true
    }
}
