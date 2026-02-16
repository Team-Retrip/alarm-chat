package com.retrip.alarm.domain.entity

import com.retrip.alarm.domain.vo.AlarmType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import java.util.UUID

@Entity
class Alarm(
    @Id
    @Column(columnDefinition = "varbinary(16)")
    val id: UUID,

    @Column(nullable = true)
    val senderId: UUID? = null, // 알림 보낸 사람 (시스템 알림일 경우 null 가능)

    @Column
    val receiverId: UUID, // 알림 받는 사람

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
