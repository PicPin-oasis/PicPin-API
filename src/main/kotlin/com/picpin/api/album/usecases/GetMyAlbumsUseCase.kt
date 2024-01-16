package com.picpin.api.album.usecases

import com.picpin.api.album.domains.Album
import com.picpin.api.album.domains.AlbumService
import com.picpin.api.album.interfaces.GetMyAlbumsResponse
import com.picpin.api.photo.domains.root.Photo
import com.picpin.api.photo.domains.root.PhotoService
import com.picpin.api.verticals.stereotype.UseCase
import org.springframework.data.domain.Pageable

@UseCase
class GetMyAlbumsUseCase(
    private val albumService: AlbumService,
    private val photoService: PhotoService
) {

    operator fun invoke(accountId: Long, pageable: Pageable): GetMyAlbumsResponse.Albums {
        val targetAlbums = albumService.readAllByOwnerId(accountId, pageable)
        val targetAlbumIds = targetAlbums.map { it.id }
        val targetPhotos = photoService.findAllByAlbumIds(targetAlbumIds)

        val albums = createAlbums(targetAlbums, targetPhotos)
        return GetMyAlbumsResponse.Albums(albums)
    }

    private fun createAlbums(targetAlbums: List<Album>, targetPhotos: List<Photo>): List<GetMyAlbumsResponse.Album> {
        val targetPhotosGroupingByAlbumId = targetPhotos.groupBy { it.albumId }

        return targetAlbums.map {
            val targetPhotosByAlbumId = targetPhotosGroupingByAlbumId[it.id] ?: emptyList()

            if (targetPhotosByAlbumId.isNotEmpty()) {
                val foundPhoto = targetPhotosByAlbumId.find { photo -> photo.coordinates.isEmpty.not() }

                GetMyAlbumsResponse.Album(
                    id = it.id,
                    title = it.title,
                    coverImageUrl = it.coverImageUrl,
                    latitude = foundPhoto?.coordinates?.coordinate?.x ?: 0.0,
                    longitude = foundPhoto?.coordinates?.coordinate?.y ?: 0.0
                )
            } else {
                GetMyAlbumsResponse.Album(it.id, it.title, it.coverImageUrl)
            }
        }
    }
}
