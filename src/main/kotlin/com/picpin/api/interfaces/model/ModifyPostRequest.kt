package com.picpin.api.interfaces.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.picpin.api.domains.base.HEX_CODE_REGEX
import com.picpin.api.usecases.model.ModifyPostCommand
import com.picpin.api.usecases.model.ModifyPostPhoto
import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class ModifyPostRequest(

    @get:Size(min = 2, max = 10)
    val title: String?,

    @get:Size(max = 100)
    val memo: String?,

    @get:Pattern(regexp = HEX_CODE_REGEX)
    @JsonProperty("marker_hex_code")
    val markerHexCode: String?,

    @JsonProperty("x")
    val longitude: String?,

    @JsonProperty("y")
    val latitude: String?,

    @get:Min(11)
    @JsonProperty("province_id")
    val provinceId: Int?,

    @JsonProperty("taken_photo_address")
    val takenPhotoAddress: String?,

    @JsonProperty("taken_photo_date")
    val takenPhotoDate: LocalDate?,

    val photos: List<ModifyPostPhotoRequest> = emptyList()
) {

    @AssertTrue(message = "The province code, taken photo address, longtude, and latitude are all required.")
    @JsonIgnore
    fun isAllowedProvinceId(): Boolean {
        return if (provinceId != null) {
            val isAllowedProvinceCode = ProvinceCode.findBy(provinceId) != null
            val isAllowedTakenPhotoAddress = takenPhotoAddress.isNullOrBlank()
            val isAllowLongitude = longitude.isNullOrBlank()
            val isAllowLatitude = latitude.isNullOrBlank()

            isAllowedProvinceCode && isAllowedTakenPhotoAddress && isAllowLongitude && isAllowLatitude
        } else {
            true
        }
    }
}

fun ModifyPostRequest.toCommand(accountId: Long, postId: Long, albumId: Long?): ModifyPostCommand =
    ModifyPostCommand(
        postId = postId,
        accountId = accountId,
        albumId = albumId,
        title = title,
        memo = memo,
        markerHexCode = markerHexCode,
        x = longitude,
        y = latitude,
        provinceId = provinceId,
        takenPhotoAddress = takenPhotoAddress,
        takenPhotoDate = takenPhotoDate,
        photos = photos.map { it.toPhoto() }
    )

data class ModifyPostPhotoRequest(
    @get:NotNull
    val id: Long,

    @get:NotBlank
    @JsonProperty("image_url")
    val imageUrl: String
)

fun ModifyPostPhotoRequest.toPhoto(): ModifyPostPhoto =
    ModifyPostPhoto(id, imageUrl)
