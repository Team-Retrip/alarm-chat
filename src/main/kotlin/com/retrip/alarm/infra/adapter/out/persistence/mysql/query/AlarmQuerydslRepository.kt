package com.retrip.alarm.infra.adapter.out.persistence.mysql.query

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.retrip.alarm.application.`in`.response.AlarmResponse
import com.retrip.alarm.application.out.repository.AlarmQueryRepository
import com.retrip.alarm.domain.entity.QAlarm.alarm
import com.retrip.alarm.infra.adapter.out.persistence.util.PageUtils
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class AlarmQuerydslRepository(
    private val query: JPAQueryFactory
) : AlarmQueryRepository {
//findAllByRecipientIdOrderByCreatedAtDesc

    override fun findAlarmsByRecipientId(recipientId: UUID, page: Pageable): Page<AlarmResponse> {
        val alarms = query
            .select(
                Projections.constructor(
                    AlarmResponse::class.java,
                    alarm.id,
                    alarm.senderId,
                    alarm.receiverId,
                    alarm.title,
                    alarm.body,
                    alarm.type,
                    alarm.isRead,
                    alarm.createdAt
                )
            )
            .from(alarm)
            .where(recipientEq(recipientId))
            .orderBy(alarm.createdAt.desc())
            .offset(page.offset)
            .limit(page.pageSize.toLong() + 1)
            .fetch()
        return PageUtils.checkEndPage(page, alarms)
    }

    override fun countAlarmCountByUnRead(recipientId: UUID): Long {
        return query
            .select(alarm.count())
            .from(alarm)
            .where(recipientEq(recipientId)?.and(alarm.isRead.eq(false)))
            .fetchOne() ?: 0
    }

    private fun recipientEq(recipientId: UUID): BooleanExpression? {
        return alarm.receiverId.eq(recipientId)
    }


}
