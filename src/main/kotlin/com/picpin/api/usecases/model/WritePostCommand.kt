package com.picpin.api.usecases.model

import com.picpin.api.domains.photo.Photo
import com.picpin.api.domains.post.Post
import com.picpin.api.domains.post.coordinate.PointFactory
import com.picpin.api.domains.post.coordinate.PostCoordinate
import java.time.LocalDate

data class WritePostCommand(
    val accountId: Long,
    val albumId: Long?,
    val title: String,
    val memo: String,
    val markerColorId: Int,
    val x: String,
    val y: String,
    val provinceId: Int,
    val takenPhotoAddress: String,
    val takenPhotoDate: LocalDate,
    val photos: List<String>
) {

    fun ifAddToOnAlbum(): Boolean = albumId != null

    fun toPostCoordinate(): PostCoordinate =
        PostCoordinate(
            accountId = accountId,
            provinceId = provinceId,
            point = PointFactory.create(x, y)
        )

    fun toPost(postCoordinateId: Long): Post =
        Post(
            albumId = albumId,
            writerId = accountId,
            title = title,
            memo = memo,
            markerColorId = markerColorId,
            takenPhotoAddress = takenPhotoAddress,
            takenPhotoDate = takenPhotoDate,
            postCoordinateId = postCoordinateId
        )

    fun toPhotos(postId: Long): List<Photo> =
        photos.map { Photo(postId, accountId, it) }
}

data class WritePostPhoto(val imageUrl: String)
