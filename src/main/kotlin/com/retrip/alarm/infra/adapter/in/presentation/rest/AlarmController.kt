package com.retrip.alarm.infra.adapter.`in`.presentation.rest

import com.retrip.alarm.application.`in`.request.CreateAlarmRequest
import com.retrip.alarm.application.`in`.response.AlarmResponse
import com.retrip.alarm.application.`in`.response.CreateAlarmsResponse
import com.retrip.alarm.application.`in`.response.UnreadCountResponse
import com.retrip.alarm.application.`in`.usecase.AlarmUseCase
import com.retrip.alarm.infra.adapter.`in`.presentation.rest.common.ApiResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("alarms")
class AlarmController(
    private val alarmUseCase: AlarmUseCase
) {

    @PostMapping("/send")
    fun sendAlarm(@RequestBody request: CreateAlarmRequest): ApiResponse<CreateAlarmsResponse> {
        val response = alarmUseCase.createAlarm(request)
        return ApiResponse.created(response)
    }

    @GetMapping
    fun getAlarms(
        @RequestParam receiverId: UUID,
        page: Pageable
    ): ApiResponse<Page<AlarmResponse>> {
        val alarms = alarmUseCase.getAlarms(receiverId, page)
        return ApiResponse.ok(alarms)
    }

    @GetMapping("/unread-count")
    fun getUnreadCount(@RequestParam receiverId: UUID): ApiResponse<UnreadCountResponse> {
        val count = alarmUseCase.getUnreadCount(receiverId)
        return ApiResponse.ok(UnreadCountResponse(count))
    }

    @PatchMapping("/{alarmId}/read")
    fun readAlarm(
        @PathVariable alarmId: UUID,
        @RequestParam receiverId: UUID
    ): ApiResponse<Unit> {
        alarmUseCase.readAlarm(alarmId, receiverId)
        return ApiResponse.ok(Unit)
    }


    @PatchMapping("/read-all")
    fun readAll(@RequestParam receiverId: UUID): ApiResponse<Unit> {
        alarmUseCase.readAll(receiverId)
        return ApiResponse.ok(Unit)
    }
}
