package com.picpin.api.usecases

import com.picpin.api.domains.album.AlbumService
import com.picpin.api.interfaces.model.GetMyAlbumResponse
import com.picpin.api.interfaces.model.GetMyAlbumsResponse
import org.springframework.stereotype.Service

@Service
class GetMyAlbumsUseCase(
    private val albumService: AlbumService
) {

    fun process(accountId: Long): GetMyAlbumsResponse {
        val albums = albumService.findAllBy(accountId)
        val getMyAlbumResponse = albums.map { GetMyAlbumResponse(it.id, it.title, it.coverImageUrl) }
        return GetMyAlbumsResponse(getMyAlbumResponse)
    }
}
