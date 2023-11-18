package com.picpin.api.post.usecases.model

import com.picpin.api.photo.domains.Photo
import com.picpin.api.post.domains.PointFactory
import com.picpin.api.post.domains.coordinate.TransientPostCoordinate
import com.picpin.api.post.domains.root.TransientPost
import java.time.LocalDate

data class ModifyPostCommand(
    val postId: Long,
    val accountId: Long,
    val albumId: Long?,
    val title: String?,
    val memo: String?,
    val markerColorId: Int,
    val x: String?,
    val y: String?,
    val provinceId: Int?,
    val takenPhotoAddress: String?,
    val takenPhotoDate: LocalDate?,
    val photos: List<ModifyPostPhoto>
) {

    fun ifAddToOnAlbum(): Boolean = albumId != null

    fun ifModifyPostCoordinate(): Boolean = provinceId != null

    fun toPostCoordinate(postCoordinateId: Long): TransientPostCoordinate =
        TransientPostCoordinate(
            id = postCoordinateId,
            accountId = accountId,
            provinceId = provinceId!!,
            point = PointFactory.create(x!!, y!!)
        )

    fun toPost(): TransientPost =
        TransientPost(
            id = postId,
            albumId = albumId,
            writerId = accountId,
            title = title,
            memo = memo,
            markerColorId = markerColorId,
            takenPhotoAddress = takenPhotoAddress,
            takenPhotoDate = takenPhotoDate
        )

    fun toNewPhotos(): List<Photo> =
        photos.filter { !it.isDeleted }.map { Photo(postId, accountId, it.imageUrl, it.id) }

    fun toDeletedPhotos(): List<Photo> =
        photos.filter { it.isDeleted }.map { Photo(postId, accountId, it.imageUrl, it.id) }
}

data class ModifyPostPhoto(val id: Long, val imageUrl: String, val isDeleted: Boolean)