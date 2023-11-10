package com.picpin.api.interfaces

import com.picpin.api.interfaces.model.CreateMyAlbumRequest
import com.picpin.api.interfaces.model.GetMyAlbumsResponse
import com.picpin.api.usecases.CreateMyAlbumsUseCase
import com.picpin.api.usecases.GetMyAlbumsUseCase
import com.picpin.api.verticals.web.model.AccountId
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/albums")
class AlbumApi(
    private val getMyAlbumsUseCase: GetMyAlbumsUseCase,
    private val createMyAlbumUseCase: CreateMyAlbumsUseCase
) {

    @GetMapping
    fun getMyAlbums(@PageableDefault pageable: Pageable, @AccountId accountId: Long): ResponseEntity<GetMyAlbumsResponse> {
        val response = getMyAlbumsUseCase.process(accountId, pageable)
        return ResponseEntity.ok(response)
    }

    @PostMapping
    fun createMyAlbum(@Valid @RequestBody request: CreateMyAlbumRequest, @AccountId accountId: Long): ResponseEntity<Unit> {
        createMyAlbumUseCase.process(request.toCommand(accountId))
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}
