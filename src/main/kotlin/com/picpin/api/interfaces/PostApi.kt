package com.picpin.api.interfaces

import com.picpin.api.interfaces.model.ModifyPostRequest
import com.picpin.api.interfaces.model.WritePostRequest
import com.picpin.api.interfaces.model.toCommand
import com.picpin.api.usecases.WritePostUseCase
import com.picpin.api.verticals.web.model.AccountId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts")
class PostApi(
    private val writePostUseCase: WritePostUseCase
) {

    @PostMapping
    fun writePost(
        @RequestBody request: WritePostRequest,
        @AccountId accountId: Long,
        @RequestParam("album_id") albumId: Long?,
    ): ResponseEntity<Unit> {
        writePostUseCase.process(request.toCommand(accountId, albumId))
        return ResponseEntity.ok().build()
    }
}
