package com.picpin.api.album.interfaces

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema
class GetMyAlbumResponse {
    data class Album(
        val id: Long,
        val title: String,
        val startDate: LocalDate?,
        val endDate: LocalDate?,
        val photoCount: Int,
        val photos: List<Photo>
    )

    data class Photo(
        val id: Long,
        val imageUrl: String
    )
}
