package com.picpin.api.verticals.interfaces.exception

import com.picpin.api.verticals.domains.exception.BusinessErrorCode
import com.picpin.api.verticals.domains.exception.BusinessException
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@ControllerAdvice
class GlobalExceptionHandler {

    private val logger: KLogger = KotlinLogging.logger { }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    protected fun handleHttpRequestMethodNotSupportedException(
        exception: HttpRequestMethodNotSupportedException
    ): ResponseEntity<String> {
        val errorMessage = exception.localizedMessage
        logger.warn { "HttpRequestMethodNotSupportedException. reason = $errorMessage" }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handleMethodArgumentNotValidException(
        exception: MethodArgumentNotValidException
    ): ResponseEntity<String> {
        val errorMessage = exception.localizedMessage
        logger.warn { "MethodArgumentNotValidException. reason = $errorMessage" }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    protected fun handleMethodArgumentTypeMismatchException(
        exception: MethodArgumentTypeMismatchException
    ): ResponseEntity<String> {
        val errorMessage = exception.localizedMessage
        logger.warn { "MethodArgumentTypeMismatchException. reason = $errorMessage" }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    protected fun handleHttpMessageNotReadableException(
        exception: HttpMessageNotReadableException
    ): ResponseEntity<String> {
        val errorMessage = exception.localizedMessage
        logger.warn { "HttpMessageNotReadableException. reason = $errorMessage" }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage)
    }

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(exception: BusinessException): ResponseEntity<ExceptionResponse> {
        val errorCode: BusinessErrorCode = exception.errorCode
        logger.info { "BusinessException. reason = ${errorCode.defaultMessage} ${exception.reason}" }

        val exceptionResponse = ExceptionResponse(errorCode.errorCode, errorCode.defaultMessage)
        return ResponseEntity.status(errorCode.httpStatus).body(exceptionResponse)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ResponseEntity<Void> {
        logger.warn { "Exception. reason = ${exception.localizedMessage}" }
        return ResponseEntity.internalServerError().build()
    }
}