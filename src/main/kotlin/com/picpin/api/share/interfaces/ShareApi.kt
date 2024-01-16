package com.picpin.api.share.interfaces

import com.picpin.api.album.interfaces.GetSharedAlbumResponse
import com.picpin.api.share.usecases.GetSharedAlbumUseCase
import com.picpin.api.share.usecases.GetSharedLinkUseCase
import com.picpin.api.verticals.interfaces.model.AccountId
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController("/share")
class ShareApi(
    private val getSharedLinkUseCase: GetSharedLinkUseCase,
    private val getSharedAlbumUseCase: GetSharedAlbumUseCase
) : ShareApiDocs {

    @GetMapping("/{albumId}")
    override fun getSharedLink(
        @Parameter(hidden = true) @AccountId accountId: Long,
        @PathVariable albumId: Long
    ): ResponseEntity<GetSharedLinkResponse.SharedLink> {
        val response = getSharedLinkUseCase(accountId, albumId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/album")
    override fun getSharedAlbumDetail(
        @RequestParam("token") token: String
    ): ResponseEntity<GetSharedAlbumResponse.Album> {
        val response = getSharedAlbumUseCase(token)
        return ResponseEntity.ok(response)
    }
}

@Tag(name = "공유 API")
interface ShareApiDocs {

    @Operation(
        method = "GET",
        summary = "해당 앨범에 대한 공유 링크 발급",
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
            ApiResponse(responseCode = "200", content = [Content(schema = Schema(implementation = GetSharedLinkResponse.SharedLink::class))])
        ]
    )
    @GetMapping("/{albumId}")
    fun getSharedLink(
        @Parameter(hidden = true) @AccountId accountId: Long,
        @PathVariable albumId: Long
    ): ResponseEntity<GetSharedLinkResponse.SharedLink>

    @Operation(
        method = "GET",
        summary = "공유 링크를 통해 앨범 상세 정보 가져오기",
        parameters = [
            Parameter(
                name = HttpHeaders.AUTHORIZATION,
                `in` = ParameterIn.HEADER,
                description = "JWT Token",
                example = "Bearer eyjhbGciOiJIUz...",
                required = true
            ),
            Parameter(
                name = "token",
                `in` = ParameterIn.QUERY,
                description = "공유 토큰 값 (공유 링크 발급 시 매핑된 token query parameter 값)",
                example = "01EX8Y21KBH49ZZCA7KSKH6X1C",
                required = true
            )
        ],
        responses = [
            ApiResponse(responseCode = "200", content = [Content(schema = Schema(implementation = GetSharedAlbumResponse.Album::class))])
        ]
    )
    @GetMapping("/album")
    fun getSharedAlbumDetail(
        @RequestParam("token") token: String
    ): ResponseEntity<GetSharedAlbumResponse.Album>
}
