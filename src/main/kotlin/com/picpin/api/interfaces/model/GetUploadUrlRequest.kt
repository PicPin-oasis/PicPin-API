package com.picpin.api.interfaces.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class GetUploadUrlRequest(
    @get:NotBlank
    @JsonProperty("image_name")
    val imageName: String
)
