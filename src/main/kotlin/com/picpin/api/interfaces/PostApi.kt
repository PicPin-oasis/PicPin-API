package com.picpin.api.interfaces

import com.picpin.api.interfaces.model.GetMyAllPosts
import com.picpin.api.interfaces.model.GetMyPostsByDate
import com.picpin.api.interfaces.model.ModifyPostRequest
import com.picpin.api.interfaces.model.WritePostRequest
import com.picpin.api.interfaces.model.toCommand
import com.picpin.api.usecases.GetMyAllPostsUseCase
import com.picpin.api.usecases.GetMyPostsByDateUseCase
import com.picpin.api.usecases.ModifyPostUseCase
import com.picpin.api.usecases.WritePostUseCase
import com.picpin.api.verticals.web.model.AccountId
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/posts")
class PostApi(
    private val writePostUseCase: WritePostUseCase,
    private val modifyPostUseCase: ModifyPostUseCase,
    private val getMyAllPostsUseCase: GetMyAllPostsUseCase,
    private val getMyPostsByDateUseCase: GetMyPostsByDateUseCase
) {

    @PostMapping
    fun writePost(
        @Valid @RequestBody request: WritePostRequest,
        @AccountId accountId: Long,
        @RequestParam("album_id") albumId: Long?
    ): ResponseEntity<Unit> {
        writePostUseCase.process(request.toCommand(accountId, albumId))
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PutMapping("/{postId}")
    fun modifyPost(
        @Valid @RequestBody request: ModifyPostRequest,
        @AccountId accountId: Long,
        @PathVariable @Min(1L) postId: Long,
        @RequestParam("album_id") albumId: Long?
    ): ResponseEntity<Unit> {
        modifyPostUseCase.process(request.toCommand(accountId, postId, albumId))
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @GetMapping
    fun getMyAllPosts(
        @AccountId accountId: Long,
        @RequestParam("only_un_mapped") onlyUnMapped: Boolean?,
        @PageableDefault pageable: Pageable
    ): ResponseEntity<GetMyAllPosts.Posts> {
        val response = getMyAllPostsUseCase.process(accountId, onlyUnMapped ?: false, pageable)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/dates")
    fun getMyPostsByDate(
        @AccountId accountId: Long,
        @PageableDefault pageable: Pageable
    ): ResponseEntity<GetMyPostsByDate.PostSections> {
        val response = getMyPostsByDateUseCase.process(accountId, pageable)
        return ResponseEntity.ok(response)
    }
}
