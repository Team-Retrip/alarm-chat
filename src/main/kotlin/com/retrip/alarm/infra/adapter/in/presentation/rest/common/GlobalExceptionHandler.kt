package com.retrip.alarm.infra.adapter.`in`.presentation.rest.common

import com.retrip.alarm.domain.exception.common.BusinessException
import com.retrip.alarm.domain.exception.common.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.nio.file.AccessDeniedException

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(request: HttpServletRequest, e: AccessDeniedException): ApiResponse<ErrorResponse> {
        log.error("handleAccessDeniedException: ", e)
        return handle(ErrorCode.HANDLE_ACCESS_DENIED, request, e.message ?: "Access Denied")
    }

    @ExceptionHandler(BindException::class)
    fun handleBindException(request: HttpServletRequest, e: BindException): ApiResponse<ErrorResponse> {
        log.error("handleBindException: ", e)
        return ApiResponse.of(
            ErrorResponse.of(
                ErrorCode.INVALID_INPUT_VALUE,
                request.requestURL.toString(),
                request.method,
                e.bindingResult
            )
        )
    }

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(request: HttpServletRequest, e: BusinessException): ApiResponse<ErrorResponse> {
        log.error("handleBusinessException: ", e)
        return handle(e.errorCode, request, e.message ?: e.errorCode.message)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(request: HttpServletRequest, e: Exception): ApiResponse<ErrorResponse> {
        log.error("handleException: ", e)
        return handle(ErrorCode.ILLEGAL_ARGUMENT, request, e.message ?: "Illegal Argument")
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(request: HttpServletRequest, e: Exception): ApiResponse<ErrorResponse> {
        log.error("handleException: ", e)
        return handle(ErrorCode.ILLEGAL_STATE, request, e.message ?: "Illegal State")
    }

    @ExceptionHandler(Exception::class)
    fun handleException(request: HttpServletRequest, e: Exception): ApiResponse<ErrorResponse> {
        log.error("handleException: ", e)
        return handle(ErrorCode.SERVER_ERROR, request, e.message ?: "Server Error")
    }

    private fun handle(errorCode: ErrorCode, request: HttpServletRequest, errorMessage: String): ApiResponse<ErrorResponse> {
        return ApiResponse.of(
            ErrorResponse.of(
                errorCode, request.requestURL.toString(), request.method, errorMessage
            )
        )
    }
}
