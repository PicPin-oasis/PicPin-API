package com.picpin.api.photo.domains.upload

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.Headers
import com.amazonaws.services.s3.model.CannedAccessControlList.PublicRead
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.picpin.api.photo.domains.models.PreSignedUrl
import com.picpin.api.verticals.domains.base.DEFAULT_ZONE_ID
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.*

@Service
class S3Service(
    private val amazonS3: AmazonS3,
    @Value("\${aws.bucket}") private val bucket: String,
    @Value("\${aws.cloud_front}") private val cloudFront: String
) {

    fun generatePreSignedUrl(imageName: String): PreSignedUrl {
        val extension = getFilenameExtension(imageName)
        validExtension(extension)

        val bucketKey = generateBucketKey(extension)
        val expiration = getExpiration()
        val preSignedUrlRequest = generatePreSignedUrlRequest(bucketKey, expiration, extension)

        val uploadUrl = amazonS3.generatePresignedUrl(preSignedUrlRequest).toString()
        return PreSignedUrl(cloudFront, bucketKey, uploadUrl)
    }

    private fun getFilenameExtension(imageName: String): String =
        when (val filenameExtension = StringUtils.getFilenameExtension(imageName)) {
            null -> {
                ""
            }
            else -> {
                filenameExtension
            }
        }

    private fun validExtension(extension: String) {
        if (!ALLOWED_IMAGE_LIST.contains(extension)) {
            throw RuntimeException()
        }
    }

    private fun generatePreSignedUrlRequest(
        bucketKey: String,
        expiration: Instant,
        extension: String
    ): GeneratePresignedUrlRequest {
        val request = GeneratePresignedUrlRequest(bucket, bucketKey)
            .withContentType(extension)
            .withMethod(HttpMethod.PUT)
            .withExpiration(Date.from(expiration))
        request.addRequestParameter(Headers.S3_CANNED_ACL, PublicRead.toString())

        return request
    }

    private fun getExpiration() = LocalDateTime.now()
        .plusMinutes(10)
        .atZone(DEFAULT_ZONE_ID)
        .toInstant()

    private fun generateBucketKey(extension: String): String {
        val filePath = "images/$extension"
        val uuid = UUID.randomUUID().toString().lowercase()

        val filename = "${uuid}_${OffsetDateTime.now().toEpochSecond()}.$extension"
        return "$filePath/$filename"
    }

    companion object {
        private val ALLOWED_IMAGE_LIST = listOf("jpg", "jpeg", "png")
        private const val JPEG_FORMAT_NAME = "jpeg"
    }
}
