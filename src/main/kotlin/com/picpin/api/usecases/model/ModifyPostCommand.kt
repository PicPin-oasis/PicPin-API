package com.picpin.api.usecases.model

import com.picpin.api.domains.post.TransientPost
import com.picpin.api.domains.post.coordinate.PointFactory
import com.picpin.api.domains.post.coordinate.TransientPostCoordinate
import java.time.LocalDate

data class ModifyPostCommand(
    val postId: Long,
    val accountId: Long,
    val albumId: Long?,
    val title: String?,
    val memo: String?,
    val markerHexCode: String?,
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
            markerHexCode = markerHexCode,
            takenPhotoAddress = takenPhotoAddress,
            takenPhotoDate = takenPhotoDate,
        )
}

data class ModifyPostPhoto(val id: Long, val imageUrl: String)
