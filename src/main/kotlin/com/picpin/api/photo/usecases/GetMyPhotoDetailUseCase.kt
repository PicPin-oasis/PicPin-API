package com.picpin.api.photo.usecases

import com.picpin.api.photo.domains.root.PhotoService
import com.picpin.api.photo.interfaces.models.GetMyPhotoDetailResponse
import com.picpin.api.verticals.stereotype.UseCase

@UseCase
class GetMyPhotoDetailUseCase(
    private val photoService: PhotoService
) {

    operator fun invoke(photoId: Long, accountId: Long): GetMyPhotoDetailResponse.PhotoDetail {
        val targetPhoto = photoService.readByOwnerId(photoId, accountId)
        return GetMyPhotoDetailResponse.PhotoDetail.from(targetPhoto)
    }
}
