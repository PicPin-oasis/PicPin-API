package com.picpin.api.photo.interfaces

import com.picpin.api.photo.interfaces.models.GetMyPhotoDetailResponse
import com.picpin.api.photo.interfaces.models.GetMyPhotoSectionsResponse
import com.picpin.api.photo.interfaces.models.GetMyPhotosResponse
import com.picpin.api.photo.interfaces.models.ModifyMyPhotoRequest
import com.picpin.api.photo.interfaces.models.SaveMyPhotosRequest
import com.picpin.api.photo.interfaces.models.UploadMyPhotosResponse
import com.picpin.api.photo.usecases.GetMyPhotoDetailUseCase
import com.picpin.api.photo.usecases.GetMyPhotoSectionsUseCase
import com.picpin.api.photo.usecases.GetMyPhotosUseCase
import com.picpin.api.photo.usecases.ModifyMyPhotoUseCase
import com.picpin.api.photo.usecases.RemoveMyPhotoUseCase
import com.picpin.api.photo.usecases.SaveMyPhotosUseCase
import com.picpin.api.photo.usecases.UploadMyPhotosUseCase
import com.picpin.api.verticals.interfaces.model.AccountId
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class PhotoApi(
    private val getMyPhotosUseCase: GetMyPhotosUseCase,
    private val getMyPhotoSectionsUseCase: GetMyPhotoSectionsUseCase,
    private val getMyPhotoDetailUseCase: GetMyPhotoDetailUseCase,
    private val saveMyPhotoUseCase: SaveMyPhotosUseCase,
    private val modifyMyPhotoUseCase: ModifyMyPhotoUseCase,
    private val removeMyPhotoUseCase: RemoveMyPhotoUseCase,
    private val uploadMyPhotosUseCase: UploadMyPhotosUseCase
) : PhotoApiDocs {

    @PostMapping("/photos/upload")
    override fun uploadMyPhotos(
        @RequestPart files: List<MultipartFile>
    ): ResponseEntity<UploadMyPhotosResponse> {
        val response = uploadMyPhotosUseCase(files)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/photos")
    override fun getMyPhotos(
        @RequestParam("search_type", required = false) rawSearchType: String?,
        @AccountId accountId: Long
    ): ResponseEntity<GetMyPhotosResponse.PhotoCards> {
        val response = getMyPhotosUseCase(rawSearchType, accountId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/photo-sections")
    override fun getMyPhotoSections(@AccountId accountId: Long): ResponseEntity<GetMyPhotoSectionsResponse.PhotoSections> {
        val response = getMyPhotoSectionsUseCase(accountId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/photos/{photoId}")
    override fun getMyPhotoDetail(@PathVariable photoId: Long, @AccountId accountId: Long): ResponseEntity<GetMyPhotoDetailResponse.PhotoDetail> {
        val response = getMyPhotoDetailUseCase(photoId, accountId)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/photos")
    override fun saveMyPhotos(
        @Valid @RequestBody request: SaveMyPhotosRequest.Photos,
        @AccountId accountId: Long
    ): ResponseEntity<Unit> {
        saveMyPhotoUseCase(request.toCommand(accountId))
        return ResponseEntity.ok().build()
    }

    @PutMapping("/photos/{photoId}")
    override fun modifyMyPhoto(
        @Valid @RequestBody request: ModifyMyPhotoRequest,
        @PathVariable photoId: Long,
        @AccountId accountId: Long
    ): ResponseEntity<Unit> {
        modifyMyPhotoUseCase(request.toCommand(photoId, accountId))
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/photos/{photoId}")
    override fun removeMyPhoto(
        @PathVariable photoId: Long,
        @AccountId accountId: Long
    ): ResponseEntity<Unit> {
        removeMyPhotoUseCase(photoId, accountId)
        return ResponseEntity.ok().build()
    }
}

@Tag(name = "사진 API")
interface PhotoApiDocs {

    @Operation(
        method = "POST",
        summary = "사진 업로드",
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
    @PostMapping("/photos/upload")
    fun uploadMyPhotos(
        @RequestPart files: List<MultipartFile>
    ): ResponseEntity<UploadMyPhotosResponse>

    @Operation(
        method = "GET",
        summary = "내 사진 목록 조회",
        parameters = [
            Parameter(
                name = HttpHeaders.AUTHORIZATION,
                `in` = ParameterIn.HEADER,
                description = "JWT Token",
                example = "Bearer eyjhbGciOiJIUz...",
                required = true
            ),
            Parameter(
                name = "search_type",
                `in` = ParameterIn.QUERY,
                description = "유형별 검색 타입, [default: ALL] (ALL: 모두, MAPPING: 앨범 등록 사진, UN_MAPPING: 앨범 미등록 사진)",
                example = "ALL",
                required = false
            )
        ]
    )
    @GetMapping("/photos")
    fun getMyPhotos(
        @Parameter(hidden = true) @RequestParam("search_type", required = false) searchType: String?,
        @Parameter(hidden = true) @AccountId accountId: Long
    ): ResponseEntity<GetMyPhotosResponse.PhotoCards>

    @Operation(
        method = "GET",
        summary = "내 날짜별 사진 목록 조회",
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
    @GetMapping("/photo-sections")
    fun getMyPhotoSections(@Parameter(hidden = true) @AccountId accountId: Long): ResponseEntity<GetMyPhotoSectionsResponse.PhotoSections>

    @Operation(
        method = "GET",
        summary = "내 사진 상세 조회",
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
    @GetMapping("/photos/{photoId}")
    fun getMyPhotoDetail(@PathVariable photoId: Long, @Parameter(hidden = true) @AccountId accountId: Long): ResponseEntity<GetMyPhotoDetailResponse.PhotoDetail>

    @Operation(
        method = "POST",
        summary = "내 사진 일괄 등록",
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
    @PostMapping("/photos")
    fun saveMyPhotos(
        @Valid @RequestBody request: SaveMyPhotosRequest.Photos,
        @Parameter(hidden = true) @AccountId accountId: Long
    ): ResponseEntity<Unit>

    @Operation(
        method = "PUT",
        summary = "내 사진 수정",
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
    @PutMapping("/photos/{photoId}")
    fun modifyMyPhoto(
        @Valid @RequestBody request: ModifyMyPhotoRequest,
        @PathVariable photoId: Long,
        @Parameter(hidden = true) @AccountId accountId: Long
    ): ResponseEntity<Unit>

    @Operation(
        method = "DELETE",
        summary = "내 사진 삭제",
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
    @DeleteMapping("/photos/{photoId}")
    fun removeMyPhoto(@PathVariable photoId: Long, @Parameter(hidden = true) @AccountId accountId: Long): ResponseEntity<Unit>
}
