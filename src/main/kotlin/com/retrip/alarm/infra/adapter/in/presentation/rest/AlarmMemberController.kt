package com.retrip.alarm.infra.adapter.`in`.presentation.rest

import com.retrip.alarm.application.`in`.request.CreateAlarmMemberRequest
import com.retrip.alarm.application.`in`.request.UpdateAlarmMemberRequest
import com.retrip.alarm.application.`in`.response.AlarmMemberResponse
import com.retrip.alarm.application.`in`.response.CreateAlarmMemberResponse
import com.retrip.alarm.application.`in`.response.UpdateAlarmMemberResponse
import com.retrip.alarm.application.`in`.usecase.AlarmMemberUseCase
import com.retrip.alarm.infra.adapter.`in`.presentation.rest.common.ApiResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("alarms/member")
class AlarmMemberController(
    private val alarmMemberUseCase: AlarmMemberUseCase
) {

    @GetMapping(value = ["{id}", ""])
    fun getAlarmMember(@PathVariable("id", required = false) id: UUID?, page: Pageable): Page<AlarmMemberResponse> {
        val response = alarmMemberUseCase.getAlarmMembers(id, page)
        return response
    }

    @PostMapping("")
    fun addAlarmMember(@RequestBody request: CreateAlarmMemberRequest): ApiResponse<CreateAlarmMemberResponse> {
        val response = alarmMemberUseCase.createAlarmMember(request)
        return ApiResponse.created(response)
    }

    @PutMapping("{id}")
    fun updateAlarmMember(
        @PathVariable("id") id: UUID,
        @RequestBody request: UpdateAlarmMemberRequest
    ): ApiResponse<UpdateAlarmMemberResponse> {
        val response = alarmMemberUseCase.updateAlarmMember(id, request)
        return ApiResponse.ok(response)
    }

    @DeleteMapping("{id}")
    fun deleteAlarmMember(@PathVariable("id") id: UUID): ApiResponse<Unit> {
        alarmMemberUseCase.deleteAlarmMember(id)
        return ApiResponse.noContent()
    }
}
