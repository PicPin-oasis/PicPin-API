package com.picpin.api.account.interfaces

import com.fasterxml.jackson.annotation.JsonProperty
import com.picpin.api.oauth.domains.model.JsonWebToken

data class SocialLoginResponse(
    @JsonProperty("access_token")
    val jsonWebToken: JsonWebToken,
    @JsonProperty("refresh_token")
    val refreshToken: JsonWebToken,
    @JsonProperty("token_type")
    val tokenType: String
)
