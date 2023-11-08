package com.picpin.api.domain.oauth.model

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoAccessToken(
    @JsonProperty("access_token")
    val payload: String,
    @JsonProperty("token_type")
    val tokenType: String,
    @JsonProperty("refresh_token")
    val refreshToken: String,
    @JsonProperty("expires_in")
    val expiresIn: String,
    @JsonProperty("refresh_token_expires_in")
    val refreshTokenExpiresIn: String,
    @JsonProperty("scope")
    val scope: String
)
