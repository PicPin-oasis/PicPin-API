package com.picpin.api.interfaces.model

import com.fasterxml.jackson.annotation.JsonProperty

data class GetUploadUrlRequest(
    @JsonProperty("image_name")
    val imageName: String
)
