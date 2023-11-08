package com.picpin.api.usecases

import com.picpin.api.domains.photo.S3Service
import com.picpin.api.domains.photo.model.UploadUrlResponse
import org.springframework.stereotype.Service

@Service
class GetUploadUrlUseCase(
    private val s3Service: S3Service
) {

    fun process(imageName: String): UploadUrlResponse {
        val preSignedUrl = s3Service.generatePreSignedUrl(imageName)
        return UploadUrlResponse(preSignedUrl.cloudFrontHost, preSignedUrl.key, preSignedUrl.uploadUrl)
    }
}
