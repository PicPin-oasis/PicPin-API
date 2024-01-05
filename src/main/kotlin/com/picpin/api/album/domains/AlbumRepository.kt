package com.picpin.api.album.domains

import com.picpin.api.verticals.domains.exception.BusinessErrorCode
import com.picpin.api.verticals.domains.exception.BusinessException
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface AlbumRepository : JpaRepository<Album, Long> {
    fun findAllByOwnerId(ownerId: Long, pageable: Pageable): List<Album>
    fun deleteByIdAndOwnerId(albumId: Long, ownerId: Long)
}

fun AlbumRepository.findOneOrThrow(albumId: Long): Album {
    return findById(albumId).orElseThrow {
        BusinessException.of(BusinessErrorCode.ALBUM_NOT_FOUND, "albumId = $albumId")
    }
}
