package com.picpin.api.verticals.interfaces.exception

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.picpin.api.verticals.domains.exception.BusinessErrorCode
import com.picpin.api.verticals.domains.exception.BusinessException
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@Order(0)
@ControllerAdvice
class GlobalExceptionHandler {

    private val logger: KLogger = KotlinLogging.logger { }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    protected fun handleHttpRequestMethodNotSupportedException(
        exception: HttpRequestMethodNotSupportedException
    ): ResponseEntity<String> {
        val errorMessage = exception.localizedMessage
        val filteredRequestHeaders = exception.headers.filter { it.key == HttpHeaders.CONTENT_TYPE || it.key == HttpHeaders.AUTHORIZATION }
        logger.warn { "HttpRequestMethodNotSupportedException. reason = ${exception.supportedHttpMethods} ${exception.supportedMethods} $filteredRequestHeaders ${exception.method} $errorMessage" }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage)
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    protected fun handleHttpMediaTypeNotSupportedException(exception: HttpMediaTypeNotSupportedException): ResponseEntity<String> {
        val errorMessage = exception.localizedMessage
        logger.warn { "HttpMediaTypeNotSupportedException. reason = ${exception.supportedMediaTypes} but ${exception.contentType} $errorMessage" }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handleMethodArgumentNotValidException(
        exception: MethodArgumentNotValidException
    ): ResponseEntity<String> {
        val errorMessage = exception.localizedMessage
        logger.warn { "MethodArgumentNotValidException. reason = ${exception.parameter} $errorMessage" }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage)
    }

    @ExceptionHandler(MissingKotlinParameterException::class)
    protected fun handleMissingKotlinParameterException(
        exception: MissingKotlinParameterException
    ): ResponseEntity<String> {
        val errorMessage = exception.localizedMessage
        logger.warn { "MissingKotlinParameterException. reason = ${exception.targetType} ${exception.parameter} ${exception.localizedMessage}" }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage)
    }

    @ExceptionHandler(BusinessException::class)
    protected fun handleBusinessException(exception: BusinessException): ResponseEntity<ExceptionResponse> {
        val errorCode: BusinessErrorCode = exception.errorCode
        logger.info { "BusinessException. reason = ${errorCode.defaultMessage} ${exception.reason}" }

        val exceptionResponse = ExceptionResponse(errorCode.errorCode, errorCode.defaultMessage)
        return ResponseEntity.status(errorCode.httpStatus).body(exceptionResponse)
    }

    @ExceptionHandler(Exception::class)
    protected fun handleException(exception: Exception): ResponseEntity<Void> {
        logger.warn { "Exception. reason = ${exception.cause} ${exception.localizedMessage} ${exception.stackTrace}" }
        return ResponseEntity.internalServerError().build()
    }
}
