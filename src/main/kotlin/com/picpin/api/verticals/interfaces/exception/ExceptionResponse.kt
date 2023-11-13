package com.picpin.api.verticals.interfaces.exception

import com.fasterxml.jackson.annotation.JsonProperty

data class ExceptionResponse(
    @JsonProperty("error_code")
    val errorCode: Int,
    @JsonProperty("error_message")
    val errorMessage: String
)
