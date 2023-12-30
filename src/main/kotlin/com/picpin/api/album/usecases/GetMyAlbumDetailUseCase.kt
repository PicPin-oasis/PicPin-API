package com.picpin.api.album.usecases

import com.picpin.api.album.domains.Album
import com.picpin.api.album.domains.AlbumService
import com.picpin.api.album.interfaces.GetMyAlbumResponse
import com.picpin.api.photo.domains.root.Photo
import com.picpin.api.photo.domains.root.PhotoService
import org.springframework.stereotype.Service

@Service
class GetMyAlbumDetailUseCase(
    private val albumService: AlbumService,
    private val photoService: PhotoService
) {

    operator fun invoke(accountId: Long, albumId: Long): GetMyAlbumResponse.Album {
        val targetAlbum = albumService.findOneOrThrow(albumId)
        val targetPhotos = photoService.readAllByAlbumId(albumId)
        return GetMyAlbumDetailAssembler(targetAlbum, targetPhotos)
    }
}

object GetMyAlbumDetailAssembler {

    operator fun invoke(album: Album, photos: List<Photo>): GetMyAlbumResponse.Album {
        if (photos.isEmpty()) {
            return GetMyAlbumResponse.Album(
                id = album.id,
                title = album.title,
                startDate = null,
                endDate = null,
                photoCount = 0,
                photos = emptyList()
            )
        }

        val sortedPhotos = photos.sortedBy { it.takenPhotoDate }
        val mappedPhotos = sortedPhotos
            .map { GetMyAlbumResponse.Photo(it.id, it.imageUrl) }

        return GetMyAlbumResponse.Album(
            id = album.id,
            title = album.title,
            startDate = sortedPhotos.first().takenPhotoDate,
            endDate = sortedPhotos.last().takenPhotoDate,
            photoCount = mappedPhotos.size,
            photos = mappedPhotos
        )
    }
}
