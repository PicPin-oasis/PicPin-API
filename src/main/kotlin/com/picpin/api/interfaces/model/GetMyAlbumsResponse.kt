package com.picpin.api.interfaces.model

import com.fasterxml.jackson.annotation.JsonProperty

data class GetMyAlbumsResponse(val albums: List<GetMyAlbumResponse>)

data class GetMyAlbumResponse(
    val id: Long,
    val title: String,
    @JsonProperty("cover_image_url")
    val coverImageUrl: String
)
