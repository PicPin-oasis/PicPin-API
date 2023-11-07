package com.picpin.api.domain.oauth.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CustomAccessToken(
    val payload: String,
    @JsonProperty("expire_time")
    val expireTime: Long
)
