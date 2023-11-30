package com.picpin.api.photo.domains.models

import com.fasterxml.jackson.annotation.JsonProperty

data class PreSignedUrl(
    @JsonProperty("cloud_front_host")
    val cloudFrontHost: String,
    val key: String,
    @JsonProperty("upload_url")
    val uploadUrl: String
)
