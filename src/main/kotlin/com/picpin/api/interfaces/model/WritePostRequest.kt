package com.picpin.api.interfaces.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.picpin.api.usecases.model.WritePostCommand
import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class WritePostRequest(
    @get:Size(min = 2, max = 10)
    val title: String,

    @get:Size(max = 100)
    val memo: String,

    @get:NotNull
    @get:Min(1)
    @JsonProperty("marker_color_id")
    val markerColorId: Int,

    @get:NotBlank
    @JsonProperty("x")
    val longitude: String,

    @get:NotBlank
    @JsonProperty("y")
    val latitude: String,

    @get:NotNull
    @get:Min(11)
    @JsonProperty("province_code")
    val provinceId: Int,

    @get:NotBlank
    @JsonProperty("taken_photo_address")
    val takenPhotoAddress: String,

    @get:NotNull
    @JsonProperty("taken_photo_date")
    val takenPhotoDate: LocalDate,

    @get:NotEmpty
    val photos: List<String>
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
}

fun WritePostRequest.toCommand(accountId: Long, albumId: Long?): WritePostCommand =
    WritePostCommand(
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
        photos = photos
    )
