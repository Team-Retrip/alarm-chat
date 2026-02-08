package com.retrip.alarm.domain.exception.common

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val code: String,
    val message: String
) {
    // Common
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Common-001", "Server error"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "Common-002", "Invalid input value"),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "Common-003", "Access is denied"),
    ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "Common-004", "Entity not found"),
    ILLEGAL_STATE(HttpStatus.BAD_REQUEST, "Common-005", "Illegal state"),
    ILLEGAL_ARGUMENT(HttpStatus.BAD_REQUEST, "Common-006", "Illegal argument"),

    // Alarm
    ALARM_NOT_FOUND(HttpStatus.NOT_FOUND, "Alarm-001", "Alarm not found"),
}
