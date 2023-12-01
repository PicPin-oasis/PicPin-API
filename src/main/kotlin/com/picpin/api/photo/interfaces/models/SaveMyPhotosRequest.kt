package com.picpin.api.photo.interfaces.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.picpin.api.photo.usecases.models.SaveMyPhotosCommand
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate

class SaveMyPhotosRequest {

    data class Photos(
        @Schema(description = "앨범 아이디", defaultValue = "앨범 아이디")
        @JsonProperty("album_id")
        val albumId: Long?,

        @Schema(description = "타이틀", defaultValue = "샘플 타이틀")
        @get:Size(min = 2, max = 10)
        @JsonProperty("place_name")
        val placeName: String,

        @Schema(description = "메모", defaultValue = "샘플 메모")
        @get:Size(max = 300)
        val memo: String?,

        @Schema(description = "사진 촬영 주소", defaultValue = "서울..")
        @get:NotBlank
        @JsonProperty("taken_photo_address")
        val takenPhotoAddress: String,

        @Schema(description = "사진 촬영 일시", defaultValue = "2023-11-03")
        @get:NotNull
        @JsonProperty("taken_photo_date")
        val takenPhotoDate: LocalDate,

        @Schema(description = "사진 이미지 경로 목록", defaultValue = "[\"../images/jpg/12123.jpg\", \"../images/jpg/12321.jpg\"]")
        @get:NotEmpty
        @JsonProperty("photo_urls")
        val photoUrls: List<String>
    ) {

        fun toCommand(accountId: Long): SaveMyPhotosCommand =
            SaveMyPhotosCommand(
                ownerId = accountId,
                albumId = albumId,
                placeName = placeName,
                memo = memo,
                takenPhotoAddress = takenPhotoAddress,
                takenPhotoDate = takenPhotoDate,
                photoUrls = photoUrls
            )
    }
}
