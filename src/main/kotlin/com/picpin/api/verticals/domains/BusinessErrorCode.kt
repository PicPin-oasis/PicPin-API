package com.picpin.api.verticals.domains

import org.springframework.http.HttpStatus

enum class BusinessErrorCode(
    val errorCode: Int,
    val defaultMessage: String,
    val httpStatus: HttpStatus
) {

    // Account
    ACCOUNT_NOT_FOUND(100, "Not found Account Id.", HttpStatus.NOT_FOUND),

    // Kakao
    GET_ACCESS_TOKEN_FAILED(900, "Failed to retrieve access token.", HttpStatus.UNAUTHORIZED),
    GET_KAKAO_USER_INFO_FAILED(901, "Failed to retrieve access token.", HttpStatus.UNAUTHORIZED),

    // JWT
    INVALID_JWT_TOKEN(1000, "", HttpStatus.UNAUTHORIZED),
    JWT_CREATE_FAILED(1003, "", HttpStatus.INTERNAL_SERVER_ERROR)
}
