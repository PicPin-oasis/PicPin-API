package com.picpin.api.domains.album

import com.picpin.api.verticals.domain.exception.BusinessErrorCode
import com.picpin.api.verticals.domain.exception.BusinessException
import org.springframework.data.jpa.repository.JpaRepository

interface AlbumRepository : JpaRepository<Album, Long> {
    fun findAllByOwnerId(ownerId: Long): List<Album>
}

fun AlbumRepository.findOneOrThrow(albumId: Long): Album {
    return findById(albumId).orElseThrow {
        BusinessException.of(BusinessErrorCode.ALBUM_NOT_FOUND, "albumId = $albumId")
    }
}
