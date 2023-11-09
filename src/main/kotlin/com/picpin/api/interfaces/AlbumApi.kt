package com.picpin.api.interfaces

import com.picpin.api.interfaces.model.GetMyAlbumsResponse
import com.picpin.api.usecases.GetMyAlbumsUseCase
import com.picpin.api.verticals.web.model.AccountId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/albums")
class AlbumApi(
    private val getMyAlbumsUseCase: GetMyAlbumsUseCase
) {

    @GetMapping
    fun getMyAlbums(@AccountId accountId: Long): ResponseEntity<GetMyAlbumsResponse> {
        val response = getMyAlbumsUseCase.process(accountId)
        return ResponseEntity.ok(response)
    }
}