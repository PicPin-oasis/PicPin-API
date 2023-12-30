package com.picpin.api.album.interfaces

import com.picpin.api.album.usecases.CreateMyAlbumsUseCase
import com.picpin.api.album.usecases.GetMyAlbumDetailUseCase
import com.picpin.api.album.usecases.GetMyAlbumsUseCase
import com.picpin.api.verticals.interfaces.model.AccountId
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/albums")
class AlbumApi(
    private val getMyAlbumsUseCase: GetMyAlbumsUseCase,
    private val getMyAlbumDetailUseCase: GetMyAlbumDetailUseCase,
    private val createMyAlbumUseCase: CreateMyAlbumsUseCase
) : AlbumApiDocs {

    @GetMapping
    override fun getMyAlbums(
        @Parameter(hidden = true) @AccountId accountId: Long,
        @PageableDefault pageable: Pageable
    ): ResponseEntity<GetMyAlbumsResponse.Albums> {
        val response = getMyAlbumsUseCase(accountId, pageable)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{albumId}")
    override fun getMyAlbumDetail(
        @Parameter(hidden = true) @AccountId accountId: Long,
        @PathVariable albumId: Long
    ): ResponseEntity<GetMyAlbumResponse.Album> {
        val response = getMyAlbumDetailUseCase(accountId, albumId)
        return ResponseEntity.ok(response)
    }

    @PostMapping
    override fun createMyAlbum(
        @Valid @RequestBody request: CreateMyAlbumRequest,
        @Parameter(hidden = true) @AccountId accountId: Long
    ): ResponseEntity<Unit> {
        createMyAlbumUseCase(request.toCommand(accountId))
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}

@Tag(name = "앨범 API")
interface AlbumApiDocs {

    @Operation(
        method = "GET",
        summary = "내 앨범 목록 조회",
        parameters = [
            Parameter(
                name = HttpHeaders.AUTHORIZATION,
                `in` = ParameterIn.HEADER,
                description = "JWT Token",
                example = "Bearer eyjhbGciOiJIUz...",
                required = true
            )
        ],
        responses = [
            ApiResponse(responseCode = "200", content = [Content(schema = Schema(implementation = GetMyAlbumsResponse.Albums::class))])
        ]
    )
    @GetMapping
    fun getMyAlbums(
        @Parameter(hidden = true) @AccountId accountId: Long,
        @PageableDefault pageable: Pageable
    ): ResponseEntity<GetMyAlbumsResponse.Albums>

    @Operation(
        method = "GET",
        summary = "내 앨범 상세 조회",
        parameters = [
            Parameter(
                name = HttpHeaders.AUTHORIZATION,
                `in` = ParameterIn.HEADER,
                description = "JWT Token",
                example = "Bearer eyjhbGciOiJIUz...",
                required = true
            )
        ],
        responses = [
            ApiResponse(responseCode = "200", content = [Content(schema = Schema(implementation = GetMyAlbumResponse.Album::class))])
        ]
    )
    @GetMapping("/{albumId}")
    fun getMyAlbumDetail(
        @Parameter(hidden = true) @AccountId accountId: Long,
        @PathVariable albumId: Long
    ): ResponseEntity<GetMyAlbumResponse.Album>

    @Operation(
        method = "POST",
        summary = "내 앨범 생성",
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
    @PostMapping
    fun createMyAlbum(
        @Valid @RequestBody request: CreateMyAlbumRequest,
        @Parameter(hidden = true) @AccountId accountId: Long
    ): ResponseEntity<Unit>
}
