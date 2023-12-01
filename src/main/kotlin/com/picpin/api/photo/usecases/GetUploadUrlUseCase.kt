package com.picpin.api.photo.usecases

import com.picpin.api.photo.domains.upload.S3Service
import com.picpin.api.photo.interfaces.UploadUrlResponse
import com.picpin.api.verticals.stereotype.UseCase

@UseCase
class GetUploadUrlUseCase(
    private val s3Service: S3Service
) {

    operator fun invoke(imageName: String): UploadUrlResponse {
        val preSignedUrl = s3Service.generatePreSignedUrl(imageName)
        return UploadUrlResponse(preSignedUrl.cloudFrontHost, preSignedUrl.key, preSignedUrl.uploadUrl)
    }
}
