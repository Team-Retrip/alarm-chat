package com.retrip.alarm.infra.adapter.in.presentation.rest.common

import com.fasterxml.jackson.annotation.JsonInclude
import com.retrip.alarm.domain.exception.common.ErrorCode
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError

@JsonInclude(JsonInclude.Include.NON_NULL)
class ErrorResponse private constructor(
    val status: Int,
    val code: String,
    val message: String,
    val url: String,
    val method: String,
    val errors: List<FieldError> = ArrayList()
) {
    companion object {
        fun of(code: ErrorCode, url: String, method: String, errorMessage: String): ErrorResponse {
            return ErrorResponse(
                status = code.status.value(),
                code = code.code,
                message = errorMessage,
                url = url,
                method = method
            )
        }

        fun of(code: ErrorCode, url: String, method: String, bindingResult: BindingResult): ErrorResponse {
            return ErrorResponse(
                status = code.status.value(),
                code = code.code,
                message = code.message,
                url = url,
                method = method,
                errors = bindingResult.fieldErrors
            )
        }

        fun of(code: ErrorCode, url: String, method: String): ErrorResponse {
            return ErrorResponse(
                status = code.status.value(),
                code = code.code,
                message = code.message,
                url = url,
                method = method
            )
        }
    }
}
