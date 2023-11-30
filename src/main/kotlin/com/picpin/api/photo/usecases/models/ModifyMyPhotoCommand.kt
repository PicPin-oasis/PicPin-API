package com.picpin.api.photo.usecases.models

import com.picpin.api.photo.domains.root.Photo
import org.locationtech.jts.geom.Point
import java.time.LocalDate

data class ModifyMyPhotoCommand(
    val ownerId: Long,
    val photoId: Long,
    val albumId: Long?,
    val placeName: String,
    val memo: String?,
    val takenPhotoAddress: String,
    val takenPhotoDate: LocalDate
) {

    fun ifAddToOnAlbum(): Boolean = albumId != null

    fun toPhoto(provinceId: Int, coordinate: Point): Photo = Photo(
        albumId = albumId,
        ownerId = ownerId,
        placeName = placeName,
        memo = memo,
        imageUrl = "",
        provinceId = provinceId,
        coordinate = coordinate,
        takenPhotoAddress = takenPhotoAddress,
        takenPhotoDate = takenPhotoDate,
        id = photoId
    )
}
