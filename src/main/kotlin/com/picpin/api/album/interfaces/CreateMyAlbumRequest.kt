package com.picpin.api.album.interfaces

import com.fasterxml.jackson.annotation.JsonProperty
import com.picpin.api.album.usecases.CreateMyAlbumCommand
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateMyAlbumRequest(
    @Schema(description = "엘범 타이틀", defaultValue = "서울..")
    @get:Size(min = 2, max = 10)
    val title: String,

    @Schema(description = "엘범 커버 이미지", defaultValue = "서울..")
    @get:NotBlank
    @JsonProperty("cover_image_url")
    val coverImageUrl: String,

    @Schema(description = "엘범 최초 생성시 포스트 입력 가능", defaultValue = "서울..")
    @JsonProperty("post_ids")
    val postIds: List<Long>
) {

    fun toCommand(accountId: Long): CreateMyAlbumCommand =
        CreateMyAlbumCommand(
            accountId = accountId,
            title = title,
            coverImageUrl = coverImageUrl,
            postIds = postIds
        )
}