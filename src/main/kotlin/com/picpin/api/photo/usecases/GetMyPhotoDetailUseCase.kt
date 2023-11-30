package com.picpin.api.photo.usecases

import com.picpin.api.photo.domains.root.PhotoService
import com.picpin.api.photo.interfaces.models.GetMyPhotoDetailResponse
import org.springframework.stereotype.Service

@Service
class GetMyPhotoDetailUseCase(
    private val photoService: PhotoService
) {

    fun process(photoId: Long, accountId: Long): GetMyPhotoDetailResponse.PhotoDetail {
        val targetPhoto = photoService.readByOwnerId(photoId, accountId)
        return GetMyPhotoDetailResponse.PhotoDetail.from(targetPhoto)
    }
}
