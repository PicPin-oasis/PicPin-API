package com.picpin.api.interfaces.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.picpin.api.domain.oauth.model.CustomAccessToken

data class OAuthResponse(
    @JsonProperty("access_token")
    val accessToken: CustomAccessToken,
    @JsonProperty("token_type")
    val tokenType: String
)
