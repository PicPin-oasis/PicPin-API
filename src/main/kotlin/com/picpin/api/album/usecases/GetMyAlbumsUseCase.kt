package com.picpin.api.album.usecases

import com.picpin.api.album.domains.Album
import com.picpin.api.album.domains.AlbumService
import com.picpin.api.album.interfaces.GetMyAlbumsResponse
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class GetMyAlbumsUseCase(
    private val albumService: AlbumService
) {

    fun process(accountId: Long, pageable: Pageable): GetMyAlbumsResponse.Albums {
        val targetAlbums = albumService.findAllByOwnerId(accountId, pageable)
        val albums = createAlbums(targetAlbums)
        return GetMyAlbumsResponse.Albums(albums)
    }

    private fun createAlbums(targetAlbums: List<Album>) =
        targetAlbums.map {
            GetMyAlbumsResponse.Album(it.id, it.title, it.coverImageUrl)
        }
}
