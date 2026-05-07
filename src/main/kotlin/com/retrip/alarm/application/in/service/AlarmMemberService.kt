package com.retrip.alarm.application.`in`.service

import com.retrip.alarm.application.`in`.request.CreateAlarmMemberRequest
import com.retrip.alarm.application.`in`.request.UpdateAlarmMemberRequest
import com.retrip.alarm.application.`in`.response.AlarmMemberResponse
import com.retrip.alarm.application.`in`.response.CreateAlarmMemberResponse
import com.retrip.alarm.application.`in`.response.UpdateAlarmMemberResponse
import com.retrip.alarm.application.out.repository.AlarmMemberQueryRepository
import com.retrip.alarm.application.`in`.usecase.AlarmMemberUseCase
import com.retrip.alarm.application.out.repository.AlarmMemberRepository
import com.retrip.alarm.domain.exception.common.BusinessException
import com.retrip.alarm.domain.exception.common.ErrorCode
import com.retrip.alarm.application.`in`.request.context.UserContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class AlarmMemberService(
    private val alarmMemberRepository: AlarmMemberRepository,
    private val alarmMemberQueryRepository: AlarmMemberQueryRepository,
) : AlarmMemberUseCase {
    override fun createAlarmMember(context: UserContext, request: CreateAlarmMemberRequest): CreateAlarmMemberResponse {
        val alarmMember = request.toAlarmMember(context.memberId)
        alarmMemberRepository.save(alarmMember)
        return CreateAlarmMemberResponse.from(alarmMember)
    }

    @Transactional(readOnly = true)
    override fun getAlarmMembers(id: UUID?, page: Pageable): Page<AlarmMemberResponse> {
        return alarmMemberQueryRepository.findAlarmMembers(id, page)
    }

    override fun updateAlarmMember(id: UUID, context: UserContext, request: UpdateAlarmMemberRequest): UpdateAlarmMemberResponse {
        val alarmMember =
            alarmMemberRepository.findById(id).orElseThrow { BusinessException(ErrorCode.ALARM_NOT_FOUND) }
        if (alarmMember.memberId != context.memberId) {
            throw BusinessException(ErrorCode.ALARM_MEMBER_NOT_FOUND)
        }
        alarmMember.update(context.memberId, request.fcmToken)
        return UpdateAlarmMemberResponse.from(alarmMember)
    }


    override fun deleteAlarmMember(id: UUID) {
        alarmMemberRepository.deleteById(id)
    }
}
