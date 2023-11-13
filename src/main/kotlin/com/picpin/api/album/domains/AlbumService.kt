package com.picpin.api.album.domains

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
    fun findAllByOwnerId(accountId: Long, pageable: Pageable): List<Album> = albumRepository.findAllByOwnerId(accountId, pageable)

    fun save(album: Album): Album = albumRepository.save(album)

    fun findAllBy(albumIds: List<Long>): List<Album> = albumRepository.findAllById(albumIds)
}
