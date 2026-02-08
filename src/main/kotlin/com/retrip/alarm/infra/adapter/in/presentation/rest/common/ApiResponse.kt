package com.retrip.alarm.infra.adapter.`in`.presentation.rest.common

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse<T>(
    val success: Boolean,
    val status: Int,
    val message: String,
    val data: T?
) {
    companion object {
        fun <T> created(data: T): ApiResponse<T> = success(data, CREATED)
        fun <T> ok(data: T): ApiResponse<T> = success(data, OK)
        fun <T> noContent(): ApiResponse<T> = success(null, NO_CONTENT)
        fun <T> of(data: T, status: HttpStatus): ApiResponse<T> = success(data, status)

        private fun <T> success(data: T?, status: HttpStatus): ApiResponse<T> {
            return ApiResponse(true, status.value(), status.reasonPhrase, data)
        }

        fun of(errorResponse: ErrorResponse): ApiResponse<ErrorResponse> {
            return ApiResponse(
                false,
                errorResponse.status,
                HttpStatus.valueOf(errorResponse.status).reasonPhrase,
                errorResponse
            )
        }
    }
}
