package com.picpin.api.photo.usecases

import com.picpin.api.album.domains.AlbumService
import com.picpin.api.photo.domains.coordinate.KakaoCoordinateProvider
import com.picpin.api.photo.domains.root.PhotoService
import com.picpin.api.photo.usecases.models.ModifyMyPhotoCommand
import com.picpin.api.verticals.stereotype.UseCase

@UseCase
class ModifyMyPhotoUseCase(
    private val albumService: AlbumService,
    private val photoService: PhotoService,
    private val kakaoCoordinateProvider: KakaoCoordinateProvider
) {

    operator fun invoke(command: ModifyMyPhotoCommand) {
        if (command.ifAddToOnAlbum()) {
            albumService.findOneOrThrow(command.albumId!!)
        }

        val coordinate = kakaoCoordinateProvider.getCoordinateByAddress(command.takenPhotoAddress)
        photoService.modifyBy(command.toPhoto(coordinate.provinceCode.id, coordinate.coordinate))
    }
}
