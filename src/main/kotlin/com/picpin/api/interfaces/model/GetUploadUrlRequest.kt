package com.picpin.api.interfaces.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class GetUploadUrlRequest(
    @Schema(description = "업로드할 이미지 파일 이름", defaultValue = "sample.jpg")
    @get:NotBlank
    @JsonProperty("image_name")
    val imageName: String
)
