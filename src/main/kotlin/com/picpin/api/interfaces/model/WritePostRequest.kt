package com.picpin.api.interfaces.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.picpin.api.domains.base.HEX_CODE_REGEX
import com.picpin.api.usecases.model.WritePostCommand
import com.picpin.api.usecases.model.WritePostPhoto
import jakarta.validation.constraints.*
import java.time.LocalDate

data class WritePostRequest(
    @get:Size(min = 2, max = 10)
    val title: String,

    @get:Size(max = 100)
    val memo: String,

    @get:NotBlank
    @get:Pattern(regexp = HEX_CODE_REGEX)
    @JsonProperty("marker_hex_code")
    val markerHexCode: String,

    @get:NotBlank
    @JsonProperty("x")
    val longitude: String,

    @get:NotBlank
    @JsonProperty("y")
    val latitude: String,

    @get:NotNull
    @get:Min(11)
    @JsonProperty("province_id")
    val provinceId: Int,

    @get:NotBlank
    @JsonProperty("taken_photo_address")
    val takenPhotoAddress: String,

    @get:NotNull
    @JsonProperty("taken_photo_date")
    val takenPhotoDate: LocalDate,

    @get:NotEmpty
    val photos: List<WritePostPhotoRequest>
) {

    @AssertTrue
    @JsonIgnore
    fun isAllowedProvinceId(): Boolean {
        val provinceCode = ProvinceCode.findBy(provinceId)

        return provinceCode != null
    }
}

fun WritePostRequest.toCommand(accountId: Long, albumId: Long?): WritePostCommand =
    WritePostCommand(
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

data class WritePostPhotoRequest(
    @get:NotBlank
    @JsonProperty("image_url")
    val imageUrl: String
)

fun WritePostPhotoRequest.toPhoto(): WritePostPhoto =
    WritePostPhoto(this.imageUrl)
