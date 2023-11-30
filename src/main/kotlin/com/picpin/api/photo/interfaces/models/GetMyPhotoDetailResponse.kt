package com.picpin.api.photo.interfaces.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.picpin.api.photo.domains.root.Photo
import java.time.LocalDate

class GetMyPhotoDetailResponse {

    data class PhotoDetail(
        val id: Long,
        @JsonProperty("place_name")
        val placeName: String,
        @JsonProperty("memo")
        val memo: String,
        @JsonProperty("expose_image_url")
        val exposeImageUrl: String,
        @JsonProperty("taken_photo_date")
        val takenPhotoDate: LocalDate
    ) {

        companion object {
            fun from(photo: Photo): PhotoDetail = PhotoDetail(
                id = photo.id,
                placeName = photo.placeName,
                memo = photo.memo,
                exposeImageUrl = photo.imageUrl,
                takenPhotoDate = photo.takenPhotoDate
            )
        }
    }
}
