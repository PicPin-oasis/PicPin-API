package com.picpin.api.interfaces

import com.picpin.api.domains.photo.model.UploadUrlResponse
import com.picpin.api.interfaces.model.GetUploadUrlRequest
import com.picpin.api.usecases.GetUploadUrlUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PhotoApi(
    private val getUploadUrlUseCase: GetUploadUrlUseCase
) {

    @PostMapping("/photos/upload-url")
    fun getUploadUrl(@RequestBody request: GetUploadUrlRequest): ResponseEntity<UploadUrlResponse> {
        val response = getUploadUrlUseCase.process(request.imageName)
        return ResponseEntity.ok(response)
    }
}
