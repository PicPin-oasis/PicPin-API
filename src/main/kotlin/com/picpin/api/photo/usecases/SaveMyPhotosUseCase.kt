package com.picpin.api.photo.usecases

import com.picpin.api.album.domains.AlbumService
import com.picpin.api.photo.domains.coordinate.KakaoCoordinateProvider
import com.picpin.api.photo.domains.root.PhotoService
import com.picpin.api.photo.usecases.models.SaveMyPhotosCommand
import org.springframework.stereotype.Service

@Service
class SaveMyPhotosUseCase(
    private val albumService: AlbumService,
    private val photoService: PhotoService,
    private val kakaoCoordinateProvider: KakaoCoordinateProvider
) {

    fun process(command: SaveMyPhotosCommand) {
        if (command.ifAddToOnAlbum()) {
            albumService.findOneOrThrow(command.albumId!!)
        }

        val coordinate = kakaoCoordinateProvider.getCoordinateByAddress(command.takenPhotoAddress)
        photoService.saveAll(command.toPhotos(coordinate.provinceCode.id, coordinate.coordinate))
    }
}
