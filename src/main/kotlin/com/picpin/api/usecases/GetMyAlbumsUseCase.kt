package com.picpin.api.usecases

import com.picpin.api.domains.album.AlbumService
import com.picpin.api.interfaces.model.GetMyAlbumResponse
import com.picpin.api.interfaces.model.GetMyAlbumsResponse
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class GetMyAlbumsUseCase(
    private val albumService: AlbumService
) {

    fun process(accountId: Long, pageable: Pageable): GetMyAlbumsResponse {
        val albums = albumService.findAllByOwnerId(accountId, pageable)
        val getMyAlbumResponse = albums.map { GetMyAlbumResponse(it.id, it.title, it.coverImageUrl) }
        return GetMyAlbumsResponse(getMyAlbumResponse)
    }
}