package com.picpin.api.domains.album

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AlbumService(
    private val albumRepository: AlbumRepository
) {

    @Transactional(readOnly = true)
    fun findOneOrThrow(albumId: Long): Album = albumRepository.findOneOrThrow(albumId)

    @Transactional(readOnly = true)
    fun findAllBy(accountId: Long): List<Album> = albumRepository.findAllByOwnerId(accountId)
}
