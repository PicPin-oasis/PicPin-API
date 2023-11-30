package com.picpin.api.photo.usecases

import com.picpin.api.photo.domains.root.PhotoService
import org.springframework.stereotype.Service

@Service
class RemoveMyPhotoUseCase(
    private val photoService: PhotoService
) {

    fun process(photoId: Long, accountId: Long) {
        photoService.removeBy(photoId, accountId)
    }
}
