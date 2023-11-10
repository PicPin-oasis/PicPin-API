package com.picpin.api.interfaces.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.picpin.api.usecases.model.ModifyPostCommand
import com.picpin.api.usecases.model.ModifyPostPhoto
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate

class ModifyPostRequest {

    data class Post(
        @Schema(description = "타이틀", defaultValue = "샘플 타이틀")
        @get:Size(min = 2, max = 10)
        val title: String,

        @Schema(description = "메모", defaultValue = "샘플 메모")
        @get:Size(max = 100)
        val memo: String,

        @Schema(description = "마커 컬러 아이디", defaultValue = "1")
        @get:NotNull
        @get:Min(1)
        @JsonProperty("marker_color_id")
        val markerColorId: Int,

        @Schema(description = "경도", defaultValue = "126.97725")
        @get:NotBlank
        @JsonProperty("x")
        val longitude: String,

        @Schema(description = "위도", defaultValue = "37.570892")
        @get:NotBlank
        @JsonProperty("y")
        val latitude: String,

        @Schema(description = "법정동 코드", defaultValue = "11")
        @get:NotNull
        @get:Min(11)
        @JsonProperty("province_code")
        val provinceId: Int,

        @Schema(description = "사진 촬영 주소", defaultValue = "서울..")
        @get:NotBlank
        @JsonProperty("taken_photo_address")
        val takenPhotoAddress: String,

        @Schema(description = "사진 촬영 일시", defaultValue = "2023-11-03")
        @get:NotNull
        @JsonProperty("taken_photo_date")
        val takenPhotoDate: LocalDate?,

        @Schema(description = "사진 이미지 경로 목록", defaultValue = "../images/jpg/12123.jpg")
        val photos: List<Photo> = emptyList()
    ) {

        @AssertTrue
        @JsonIgnore
        fun isAllowedProvinceId(): Boolean {
            return ProvinceCode.findBy(provinceId) != null
        }

        @AssertTrue
        @JsonIgnore
        fun isAllowedMarkerColorId(): Boolean {
            return MarkerColorCode.findBy(markerColorId) != null
        }

        fun toCommand(accountId: Long, postId: Long, albumId: Long?): ModifyPostCommand =
            ModifyPostCommand(
                postId = postId,
                accountId = accountId,
                albumId = albumId,
                title = title,
                memo = memo,
                markerColorId = MarkerColorCode.findBy(markerColorId)!!.id,
                x = longitude,
                y = latitude,
                provinceId = provinceId,
                takenPhotoAddress = takenPhotoAddress,
                takenPhotoDate = takenPhotoDate,
                photos = photos.map { it.toPhoto() }
            )
    }

    data class Photo(
        @get:NotNull
        val id: Long?,

        @get:NotBlank
        @JsonProperty("image_url")
        val imageUrl: String,

        @JsonProperty("is_deleted")
        val isDeleted: Boolean
    ) {

        fun toPhoto(): ModifyPostPhoto =
            ModifyPostPhoto(id ?: 0, imageUrl, isDeleted)
    }
}
