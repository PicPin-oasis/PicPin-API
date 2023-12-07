package com.picpin.api.photo.usecases

import com.picpin.api.photo.domains.upload.S3Service
import com.picpin.api.photo.interfaces.models.UploadMyPhotosResponse
import com.picpin.api.verticals.stereotype.UseCase
import org.springframework.web.multipart.MultipartFile

@UseCase
class UploadMyPhotosUseCase(
    private val s3Service: S3Service
) {

    operator fun invoke(multipartFiles: List<MultipartFile>): UploadMyPhotosResponse {
        val result = s3Service.uploadImageFiles(multipartFiles)
        return UploadMyPhotosResponse(result.first, result.second)
    }
}
