package com.picpin.api.album.interfaces

import com.fasterxml.jackson.annotation.JsonProperty

class GetMyAlbumsResponse {
    data class Albums(
        val albums: List<Album>
    )

    data class Album(
        val id: Long,
        val title: String,
        @JsonProperty("cover_image_url")
        val coverImageUrl: String
    )
}
