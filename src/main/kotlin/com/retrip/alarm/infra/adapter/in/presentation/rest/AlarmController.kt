package com.retrip.alarm.infra.adapter.in.presentation.rest

import com.retrip.alarm.application.in.request.CreateAlarmRequest
import com.retrip.alarm.application.in.response.AlarmResponse
import com.retrip.alarm.application.in.response.UnreadCountResponse
import com.retrip.alarm.application.in.usecase.AlarmUseCase
import com.retrip.alarm.infra.adapter.in.presentation.rest.common.ApiResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/alarms")
class AlarmController(
    private val alarmUseCase: AlarmUseCase
) {

    @PostMapping("/send")
    fun sendAlarm(@RequestBody request: CreateAlarmRequest): ApiResponse<AlarmResponse> {
        val alarm = alarmUseCase.createAlarm(
            recipientId = request.recipientId,
            senderId = request.senderId,
            senderName = request.senderName,
            tripId = request.tripId,
            tripTitle = request.tripTitle,
            type = request.type,
            token = request.fcmToken
        )
        return ApiResponse.created(AlarmResponse.from(alarm))
    }

    @GetMapping
    fun getAlarms(
        @RequestParam recipientId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int // Default size 20 as per PRD
    ): ApiResponse<List<AlarmResponse>> {
        val alarms = alarmUseCase.getAlarms(recipientId, page, size)
        return ApiResponse.ok(alarms.map { AlarmResponse.from(it) })
    }

    @GetMapping("/unread-count")
    fun getUnreadCount(@RequestParam recipientId: Long): ApiResponse<UnreadCountResponse> {
        val count = alarmUseCase.getUnreadCount(recipientId)
        return ApiResponse.ok(UnreadCountResponse(count))
    }

    @PatchMapping("/{alarmId}/read")
    fun readAlarm(
        @PathVariable alarmId: Long,
        @RequestParam recipientId: Long
    ): ApiResponse<Unit> {
        alarmUseCase.readAlarm(alarmId, recipientId)
        return ApiResponse.ok(Unit)
    }

    @PatchMapping("/read-all")
    fun readAll(@RequestParam recipientId: Long): ApiResponse<Unit> {
        alarmUseCase.readAll(recipientId)
        return ApiResponse.ok(Unit)
    }
}
