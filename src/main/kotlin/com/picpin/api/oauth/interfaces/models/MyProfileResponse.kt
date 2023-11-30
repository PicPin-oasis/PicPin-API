package com.picpin.api.oauth.interfaces.models

import com.fasterxml.jackson.annotation.JsonProperty

data class MyProfileResponse(
    val id: Long,
    @JsonProperty("profile_image_url")
    val profileImageUrl: String,
    val username: String
)
