package com.picpin.api.interfaces.model

data class OAuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String
)
