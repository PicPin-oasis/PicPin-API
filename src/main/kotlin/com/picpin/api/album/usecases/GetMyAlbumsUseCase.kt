package com.picpin.api.album.usecases

import com.picpin.api.album.domains.Album
import com.picpin.api.album.domains.AlbumService
import com.picpin.api.album.interfaces.GetMyAlbumsResponse
import com.picpin.api.verticals.stereotype.UseCase
import org.springframework.data.domain.Pageable

@UseCase
class GetMyAlbumsUseCase(
    private val albumService: AlbumService
) {

    operator fun invoke(accountId: Long, pageable: Pageable): GetMyAlbumsResponse.Albums {
        val targetAlbums = albumService.findAllByOwnerId(accountId, pageable)
        val albums = createAlbums(targetAlbums)
        return GetMyAlbumsResponse.Albums(albums)
    }

    private fun createAlbums(targetAlbums: List<Album>) =
        targetAlbums.map {
            GetMyAlbumsResponse.Album(it.id, it.title, it.coverImageUrl)
        }
}
