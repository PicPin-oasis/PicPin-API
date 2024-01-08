package com.picpin.api.album.domains

import com.picpin.api.verticals.domains.exception.BusinessErrorCode
import com.picpin.api.verticals.domains.exception.BusinessException
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AlbumService(
    private val albumRepository: AlbumRepository
) {

    @Transactional(readOnly = true)
    fun findOneOrThrow(albumId: Long): Album = albumRepository.findOneOrThrow(albumId)

    @Transactional(readOnly = true)
    fun readAllByOwnerId(accountId: Long, pageable: Pageable): List<Album> =
        albumRepository.findAllByOwnerId(accountId, pageable)

    fun save(album: Album): Album = albumRepository.save(album)

    @Transactional
    fun modifyBy(album: Album): Album {
        val targetAlbum = albumRepository.findOneOrThrow(album.id)
        if (targetAlbum.isOwner(album.ownerId).not()) {
            throw BusinessException.from(BusinessErrorCode.THIS_ACCOUNT_IS_NOT_OWNER)
        }

        targetAlbum.update(album)
        return targetAlbum
    }

    @Transactional
    fun removeBy(albumId: Long, ownerId: Long) {
        albumRepository.deleteByIdAndOwnerId(albumId, ownerId)
    }
}
