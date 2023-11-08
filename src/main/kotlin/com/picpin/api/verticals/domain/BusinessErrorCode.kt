package com.picpin.api.verticals.domain

import org.springframework.http.HttpStatus

enum class BusinessErrorCode(
    val errorCode: Int,
    val defaultMessage: String,
    val httpStatus: HttpStatus
)
