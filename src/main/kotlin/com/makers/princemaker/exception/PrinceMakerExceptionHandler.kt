package com.makers.princemaker.exception

import com.makers.princemaker.code.PrinceMakerErrorCode
import com.makers.princemaker.dto.PrinceMakerErrorResponse
import com.makers.princemaker.exception.PrinceMakerException
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import javax.servlet.http.HttpServletRequest

/**
 * @author Snow
 */
@ControllerAdvice
class PrinceMakerExceptionHandler {
    @ExceptionHandler(PrinceMakerException::class)
    @ResponseBody
    fun handlePrinceMakerException(
        e: PrinceMakerException,
        request: HttpServletRequest
    ): PrinceMakerErrorResponse {
        return PrinceMakerErrorResponse(
            e.princeMakerErrorCode,
            e.detailMessage
        )
    }

    @ExceptionHandler(
        value = [HttpRequestMethodNotSupportedException::class, MethodArgumentNotValidException::class
        ]
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleBadRequest(
        e: Exception,
        request: HttpServletRequest
    ): PrinceMakerErrorResponse {
        return PrinceMakerErrorResponse(
            PrinceMakerErrorCode.INVALID_REQUEST,
            PrinceMakerErrorCode.INVALID_REQUEST.message
        )
    }


    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleException(
        e: Exception,
        request: HttpServletRequest
    ): PrinceMakerErrorResponse {
        return PrinceMakerErrorResponse(
            PrinceMakerErrorCode.INTERNAL_SERVER_ERROR,
            PrinceMakerErrorCode.INTERNAL_SERVER_ERROR.message
        )
    }
}
