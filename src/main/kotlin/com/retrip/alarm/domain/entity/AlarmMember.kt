package com.retrip.alarm.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "alarm_member")
class AlarmMember(
    @Id
    @Column(columnDefinition = "varbinary(16)")
    val id: UUID,

    @Column(columnDefinition = "varbinary(16)")
    var memberId: UUID,

    @Column
    var fcmToken: String,
) : BaseEntity() {

    fun update(memberId: UUID, fcmToken: String) {
        this.memberId = memberId
        this.fcmToken = fcmToken
    }

}
