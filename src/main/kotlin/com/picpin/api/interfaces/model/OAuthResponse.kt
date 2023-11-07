package com.picpin.api.interfaces.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.picpin.api.domain.oauth.model.GenerateAccessToken

data class OAuthResponse(
    @JsonProperty("access_token")
    val accessToken: GenerateAccessToken,
    @JsonProperty("token_type")
    val tokenType: String
)
