package com.picpin.api.share.usecases

import com.picpin.api.album.domains.Album
import com.picpin.api.album.domains.AlbumService
import com.picpin.api.album.interfaces.GetSharedAlbumResponse
import com.picpin.api.photo.domains.root.Photo
import com.picpin.api.photo.domains.root.PhotoService
import com.picpin.api.share.domains.ShareTokenService
import com.picpin.api.verticals.stereotype.UseCase

@UseCase
class GetSharedAlbumUseCase(
    private val sharedTokenService: ShareTokenService,
    private val albumService: AlbumService,
    private val photoService: PhotoService
) {

    operator fun invoke(token: String): GetSharedAlbumResponse.Album {
        val shareToken = sharedTokenService.findByPayload(token)
        val albumId = shareToken.albumId

        val targetAlbum = albumService.findOneOrThrow(albumId)
        val targetPhotos = photoService.findAllByAlbumId(albumId)
        return GetSharedAlbumAssembler(targetAlbum, targetPhotos)
    }
}

object GetSharedAlbumAssembler {

    operator fun invoke(album: Album, photos: List<Photo>): GetSharedAlbumResponse.Album {
        if (photos.isEmpty()) {
            return GetSharedAlbumResponse.Album(
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
            .map { GetSharedAlbumResponse.Photo(it.id, it.imageUrl) }

        return GetSharedAlbumResponse.Album(
            id = album.id,
            title = album.title,
            startDate = sortedPhotos.first().takenPhotoDate,
            endDate = sortedPhotos.last().takenPhotoDate,
            photoCount = mappedPhotos.size,
            photos = mappedPhotos
        )
    }
}
