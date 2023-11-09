package com.picpin.api.interfaces.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.picpin.api.usecases.model.CreateMyAlbumCommand
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateMyAlbumRequest(
    @get:Size(min = 2, max = 10)
    val title: String,
    @get:NotBlank
    @JsonProperty("cover_image_url")
    val coverImageUrl: String,
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
