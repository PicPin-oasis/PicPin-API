package com.picpin.api.photo.domains.upload

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.OffsetDateTime
import java.util.*

@Service
class S3Service(
    private val amazonS3: AmazonS3,
    @Value("\${aws.bucket}") private val bucketName: String,
    @Value("\${aws.cloud_front}") private val cloudFront: String
) {
    val logger: KLogger = KotlinLogging.logger { }

    fun uploadImageFiles(imageFiles: List<MultipartFile>): Pair<MutableList<String>, MutableList<String>> {
        val failedUploadImageFiles = mutableListOf<String>()
        val successUploadImageFiles = mutableListOf<String>()

        imageFiles.map {
            try {
                val contentType = it.contentType
                if (validateMimeType(contentType).not()) {
                    failedUploadImageFiles.add(it.originalFilename ?: "unknown")
                    return@map
                }

                val metadata = ObjectMetadata().apply {
                    this.contentType = contentType
                    this.contentLength = it.size
                }
                val bucketKey = generateBucketKey(contentType!!.split("/").last())
                sendPutRequestToS3(bucketKey, it, metadata)

                successUploadImageFiles.add(buildImageUrlPathForCdn(bucketKey))
            } catch (exception: Exception) {
                logger.info { "S3Service.upload() failed. message = ${exception.localizedMessage}, file = ${it.originalFilename} ${it.contentType} ${it.size}" }
                failedUploadImageFiles.add(it.originalFilename ?: "unknown")
            }
        }

        return successUploadImageFiles to failedUploadImageFiles
    }

    private fun validateMimeType(contentType: String?) = ALLOWED_MIME_CONTENT_TYPES.contains(contentType)

    private fun sendPutRequestToS3(bucketKey: String, it: MultipartFile, metadata: ObjectMetadata) {
        val request = PutObjectRequest(bucketName, bucketKey, it.inputStream, metadata)
        amazonS3.putObject(request)
    }

    private fun buildImageUrlPathForCdn(bucketKey: String) = "$cloudFront/$bucketKey"

    private fun generateBucketKey(extension: String): String {
        val filePath = "images/$extension"
        val uuid = UUID.randomUUID().toString().lowercase()

        val filename = "${uuid}_${OffsetDateTime.now().toEpochSecond()}.$extension"
        return "$filePath/$filename"
    }

    companion object {
        private val ALLOWED_MIME_CONTENT_TYPES = listOf("image/jpeg", "image/png")
    }
}
