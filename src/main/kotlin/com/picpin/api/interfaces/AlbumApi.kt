package com.picpin.api.interfaces

import com.picpin.api.interfaces.model.CreateMyAlbumRequest
import com.picpin.api.interfaces.model.GetMyAlbumsResponse
import com.picpin.api.usecases.CreateMyAlbumsUseCase
import com.picpin.api.usecases.GetMyAlbumsUseCase
import com.picpin.api.verticals.web.model.AccountId
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "앨범 API")
@RestController
@RequestMapping("/albums")
class AlbumApi(
    private val getMyAlbumsUseCase: GetMyAlbumsUseCase,
    private val createMyAlbumUseCase: CreateMyAlbumsUseCase
) {

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
        ]
    )
    @GetMapping
    fun getMyAlbums(
        @Parameter(hidden = true) @AccountId accountId: Long,
        @PageableDefault pageable: Pageable
    ): ResponseEntity<GetMyAlbumsResponse.Albums> {
        val response = getMyAlbumsUseCase.process(accountId, pageable)
        return ResponseEntity.ok(response)
    }

    @PostMapping
    fun createMyAlbum(
        @Valid @RequestBody request: CreateMyAlbumRequest,
        @Parameter(hidden = true) @AccountId accountId: Long
    ): ResponseEntity<Unit> {
        createMyAlbumUseCase.process(request.toCommand(accountId))
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}
