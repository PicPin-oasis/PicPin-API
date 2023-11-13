package com.picpin.api.photo.interfaces

import com.fasterxml.jackson.annotation.JsonProperty

data class UploadUrlResponse(
    @JsonProperty("cloud_front_host")
    val cloudFrontHost: String,
    val key: String,
    @JsonProperty("upload_url")
    val uploadUrl: String
)
