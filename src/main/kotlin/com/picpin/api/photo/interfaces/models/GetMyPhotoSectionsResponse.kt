package com.picpin.api.photo.interfaces.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.picpin.api.photo.domains.root.Photo
import java.time.LocalDate

class GetMyPhotoSectionsResponse {
    data class PhotoSections(
        @JsonProperty("photo_sections")
        val postSections: List<PhotoSection>
    )

    data class PhotoSection(
        @JsonProperty("taken_photo_date")
        val takenPhotoDate: LocalDate,
        @JsonProperty("photo_cards")
        val photoCards: List<PhotoCard>
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
