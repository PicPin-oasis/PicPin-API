package com.picpin.api.photo.interfaces.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.picpin.api.photo.domains.root.Photo

class GetMyPhotosResponse {
    data class PhotoCards(
        @JsonProperty("photos")
        val postSections: List<PhotoCard>
    )

    data class PhotoCard(
        val id: Long,
        @JsonProperty("expose_image_url")
        val exposeImageUrl: String
    ) {

        companion object {
            fun create(photo: Photo): PhotoCard {
                return PhotoCard(photo.id, photo.imageUrl)
            }
        }
    }
}
