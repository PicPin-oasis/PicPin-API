package com.picpin.api.photo.usecases.models

import com.picpin.api.photo.domains.root.Photo
import org.locationtech.jts.geom.Point
import java.time.LocalDate

data class SaveMyPhotosCommand(
    val ownerId: Long,
    val albumId: Long?,
    val placeName: String,
    val memo: String?,
    val takenPhotoAddress: String,
    val takenPhotoDate: LocalDate,
    val photoUrls: List<String>
) {

    fun ifAddToOnAlbum(): Boolean = albumId != null

    fun toPhotos(provinceId: Int, coordinate: Point): List<Photo> =
        photoUrls.map { photoUrl ->
            Photo(
                albumId = albumId,
                ownerId = ownerId,
                placeName = placeName,
                memo = memo,
                imageUrl = photoUrl,
                provinceId = provinceId,
                coordinates = coordinate,
                takenPhotoAddress = takenPhotoAddress,
                takenPhotoDate = takenPhotoDate,
                id = 0
            )
        }
}
