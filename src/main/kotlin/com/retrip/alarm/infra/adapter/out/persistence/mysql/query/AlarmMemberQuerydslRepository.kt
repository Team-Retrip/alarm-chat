package com.retrip.alarm.infra.adapter.out.persistence.mysql.query

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.retrip.alarm.application.`in`.response.AlarmMemberResponse
import com.retrip.alarm.application.out.repository.AlarmMemberQueryRepository
import com.retrip.alarm.domain.entity.QAlarmMember.alarmMember
import com.retrip.alarm.infra.adapter.out.persistence.util.PageUtils
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class AlarmMemberQuerydslRepository(
    private val query: JPAQueryFactory
) : AlarmMemberQueryRepository {
    override fun findAlarmMembers(id: UUID?, page: Pageable): Page<AlarmMemberResponse> {
        val alarmMembers = query
            .select(
                Projections.constructor(
                    AlarmMemberResponse::class.java,
                    alarmMember.id,
                    alarmMember.memberId,
                    alarmMember.fcmToken
                )
            )
            .from(alarmMember)
            .where(memberIdEq(id))
            .offset(page.offset)
            .limit(page.pageSize.toLong() + 1)
            .fetch()
        return PageUtils.checkEndPage(page, alarmMembers)
    }

    private fun memberIdEq(id: UUID?): BooleanExpression? {
        return id?.let {
            alarmMember.memberId.eq(it)
        }
    }
}
