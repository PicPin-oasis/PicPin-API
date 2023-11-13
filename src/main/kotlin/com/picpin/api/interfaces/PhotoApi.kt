package com.picpin.api.interfaces

import com.picpin.api.domains.photo.model.UploadUrlResponse
import com.picpin.api.interfaces.model.GetUploadUrlRequest
import com.picpin.api.usecases.GetUploadUrlUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "사진 API")
@RestController
class PhotoApi(
    private val getUploadUrlUseCase: GetUploadUrlUseCase
) {

    @Operation(
        method = "POST",
        summary = "사진 업로드 경로",
        parameters = [
            Parameter(
                name = HttpHeaders.AUTHORIZATION,
                `in` = ParameterIn.HEADER,
                description = "JWT Token",
                example = "Bearer eyjhbGciOiJIUz...",
                required = true
            )
        ]
    )
    @PostMapping("/photos/upload-url")
    fun getUploadUrl(
        @Valid @RequestBody request: GetUploadUrlRequest
    ): ResponseEntity<UploadUrlResponse> {
        val response = getUploadUrlUseCase.process(request.imageName)
        return ResponseEntity.ok(response)
    }
}
