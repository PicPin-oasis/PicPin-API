package com.picpin.api.photo.usecases

import com.picpin.api.photo.domains.root.PhotoService
import com.picpin.api.verticals.stereotype.UseCase

@UseCase
class RemoveMyPhotoUseCase(
    private val photoService: PhotoService
) {

    operator fun invoke(photoId: Long, accountId: Long) {
        photoService.removeBy(photoId, accountId)
    }
}
