package com.picpin.api.interfaces

import com.picpin.api.interfaces.model.ModifyPostRequest
import com.picpin.api.interfaces.model.WritePostRequest
import com.picpin.api.interfaces.model.toCommand
import com.picpin.api.usecases.ModifyPostUseCase
import com.picpin.api.usecases.WritePostUseCase
import com.picpin.api.verticals.web.model.AccountId
import jakarta.validation.constraints.Min
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/posts")
class PostApi(
    private val writePostUseCase: WritePostUseCase,
    private val modifyPostUseCase: ModifyPostUseCase
) {

    @PostMapping
    fun writePost(
        @RequestBody request: WritePostRequest,
        @AccountId accountId: Long,
        @RequestParam("album_id") albumId: Long?,
    ): ResponseEntity<Unit> {
        writePostUseCase.process(request.toCommand(accountId, albumId))
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PutMapping("/{postId}")
    fun modifyPost(
        @RequestBody request: ModifyPostRequest,
        @AccountId accountId: Long,
        @PathVariable @Min(1L) postId: Long,
        @RequestParam("album_id") albumId: Long?,
    ): ResponseEntity<Unit> {
        modifyPostUseCase.process(request.toCommand(accountId, postId, albumId))
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}
