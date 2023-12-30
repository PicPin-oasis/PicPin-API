package com.picpin.api.photo.domains.root

import com.picpin.api.verticals.domains.exception.BusinessErrorCode
import com.picpin.api.verticals.domains.exception.BusinessException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface PhotoRepository : JpaRepository<Photo, Long>, JpaSpecificationExecutor<Photo> {

    fun findAllByOwnerId(ownerId: Long): List<Photo>

    fun findAllByAlbumId(albumId: Long): List<Photo>

    fun deleteByIdAndOwnerId(id: Long, ownerId: Long)
}

fun PhotoRepository.findOneOrThrow(photoId: Long): Photo {
    return findById(photoId).orElseThrow {
        BusinessException.of(BusinessErrorCode.PHOTO_NOT_FOUND, "photoId = $photoId")
    }
}
