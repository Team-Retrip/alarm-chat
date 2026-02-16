package com.retrip.alarm.infra.adapter.`in`.presentation.rest

import com.retrip.alarm.application.`in`.request.CreateAlarmRequest
import com.retrip.alarm.application.`in`.response.AlarmResponse
import com.retrip.alarm.application.`in`.response.CreateAlarmsResponse
import com.retrip.alarm.application.`in`.response.UnreadCountResponse
import com.retrip.alarm.application.`in`.usecase.AlarmUseCase
import com.retrip.alarm.infra.adapter.`in`.presentation.rest.common.ApiResponse
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/alarms")
class AlarmController(
    private val alarmUseCase: AlarmUseCase
) {

    @Schema(description = "알림 Push")
    @PostMapping("/send")
    fun sendAlarm(@RequestBody request: CreateAlarmRequest): ApiResponse<CreateAlarmsResponse> {
        val response = alarmUseCase.createAlarm(request)
        return ApiResponse.created(response)
    }

    @Schema(description = "알림 조회")
    @GetMapping
    fun getAlarms(
        @RequestParam receiverId: UUID,
        page: Pageable
    ): ApiResponse<Page<AlarmResponse>> {
        val alarms = alarmUseCase.getAlarms(receiverId, page)
        return ApiResponse.ok(alarms)
    }

    @Schema(description = "읽은 않은 알림 조회")
    @GetMapping("/unread-count")
    fun getUnreadCount(@RequestParam receiverId: UUID): ApiResponse<UnreadCountResponse> {
        val count = alarmUseCase.getUnreadCount(receiverId)
        return ApiResponse.ok(UnreadCountResponse(count))
    }

    @Schema(description = "알림 읽기")
    @PatchMapping("/{alarmId}/read")
    fun readAlarm(
        @PathVariable alarmId: UUID,
        @RequestParam receiverId: UUID
    ): ApiResponse<Unit> {
        alarmUseCase.readAlarm(alarmId, receiverId)
        return ApiResponse.ok(Unit)
    }


    @Schema(description = "알림 모두 읽기")
    @PatchMapping("/read-all")
    fun readAll(@RequestParam receiverId: UUID): ApiResponse<Unit> {
        alarmUseCase.readAll(receiverId)
        return ApiResponse.ok(Unit)
    }
}
