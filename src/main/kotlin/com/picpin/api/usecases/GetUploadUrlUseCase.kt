package com.picpin.api.usecases

import com.picpin.api.domain.photo.S3Service
import com.picpin.api.domain.photo.model.UploadUrlResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GetUploadUrlUseCase(
    private val s3Service: S3Service,
    @Value("\${aws.cloud_front}") private val cloudFront: String
) {

    fun process(imageName: String): UploadUrlResponse {
        val (uploadUrl, key) = s3Service.generatePreSignedUrl(imageName)

        return UploadUrlResponse(cloudFront, key, uploadUrl)
    }
}
