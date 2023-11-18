package com.picpin.api.post.interfaces

import com.picpin.api.post.interfaces.models.GetMyAllPostsResponse
import com.picpin.api.post.interfaces.models.GetMyPostsByAlbumsResponse
import com.picpin.api.post.interfaces.models.GetMyPostsByDatesResponse
import com.picpin.api.post.interfaces.models.ModifyPostRequest
import com.picpin.api.post.interfaces.models.WritePostRequest
import com.picpin.api.post.usecases.GetMyAllPostsUseCase
import com.picpin.api.post.usecases.GetMyPostsByAlbumsUseCase
import com.picpin.api.post.usecases.GetMyPostsByDateUseCase
import com.picpin.api.post.usecases.ModifyPostUseCase
import com.picpin.api.post.usecases.WritePostUseCase
import com.picpin.api.verticals.interfaces.model.AccountId
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpHeaders
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

@Tag(name = "포스트 API")
@Validated
@RestController
class PostApi(
    private val writePostUseCase: WritePostUseCase,
    private val modifyPostUseCase: ModifyPostUseCase,
    private val getMyAllPostsUseCase: GetMyAllPostsUseCase,
    private val getMyPostsByDateUseCase: GetMyPostsByDateUseCase,
    private val getMyPostsByAlbumsUseCase: GetMyPostsByAlbumsUseCase
) {

    @Operation(
        method = "POST",
        summary = "내 포스트 생성",
        parameters = [
            Parameter(
                name = HttpHeaders.AUTHORIZATION,
                `in` = ParameterIn.HEADER,
                description = "JWT Token",
                example = "Bearer eyjhbGciOiJIUz...",
                required = true
            ),
            Parameter(
                name = "post_id",
                description = "포스트 아이디",
                example = "1",
                required = true
            ),
            Parameter(
                name = "album_id",
                description = "포스트를 저장할 앨범의 아이디",
                example = "1",
                required = false
            )
        ]
    )
    @PostMapping("/posts")
    fun writePost(
        @Valid @RequestBody request: WritePostRequest.Post,
        @Parameter(hidden = true) accountId: Long,
        @RequestParam("album_id") albumId: Long?
    ): ResponseEntity<Unit> {
        writePostUseCase.process(request.toCommand(accountId, albumId))
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @Operation(
        method = "PUT",
        summary = "내 포스트 수정",
        parameters = [
            Parameter(
                name = HttpHeaders.AUTHORIZATION,
                `in` = ParameterIn.HEADER,
                description = "JWT Token",
                example = "Bearer eyjhbGciOiJIUz...",
                required = true
            ),
            Parameter(
                name = "post_id",
                description = "포스트 아이디",
                example = "1",
                required = true
            ),
            Parameter(
                name = "album_id",
                description = "포스트를 저장할 앨범의 아이디",
                example = "1",
                required = false
            )
        ]
    )
    @PutMapping("/posts/{post_id}")
    fun modifyPost(
        @Valid @RequestBody request: ModifyPostRequest.Post,
        @Parameter(hidden = true) @AccountId accountId: Long,
        @PathVariable("post_id") @Min(1L) postId: Long,
        @RequestParam("album_id") albumId: Long?
    ): ResponseEntity<Unit> {
        modifyPostUseCase.process(request.toCommand(accountId, postId, albumId))
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @Operation(
        method = "GET",
        summary = "내 포스트 전체 OR 앨범 미등록 목록 조회",
        parameters = [
            Parameter(
                name = HttpHeaders.AUTHORIZATION,
                `in` = ParameterIn.HEADER,
                description = "JWT Token",
                example = "Bearer eyjhbGciOiJIUz...",
                required = true
            ),
            Parameter(
                name = "only_un_mapped",
                `in` = ParameterIn.QUERY,
                description = "페이지 번호",
                example = "0",
                required = false
            )
        ]
    )
    @GetMapping("/posts")
    fun getMyAllPosts(
        @Parameter(hidden = true) @AccountId accountId: Long,
        @RequestParam("only_un_mapped") onlyUnMapped: Boolean?,
        @PageableDefault pageable: Pageable
    ): ResponseEntity<GetMyAllPostsResponse.Posts> {
        val response = getMyAllPostsUseCase.process(accountId, onlyUnMapped ?: false, pageable)
        return ResponseEntity.ok(response)
    }

    @Operation(
        method = "GET",
        summary = "날짜별 내 포스트 목록 조회",
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
    @GetMapping("/post-sections")
    fun getMyPostsByDates(
        @Parameter(hidden = true) @AccountId accountId: Long,
        @PageableDefault pageable: Pageable
    ): ResponseEntity<GetMyPostsByDatesResponse.PostSections> {
        val response = getMyPostsByDateUseCase.process(accountId, pageable)
        return ResponseEntity.ok(response)
    }

    @Operation(
        method = "GET",
        summary = "앨범별 내 포스트 목록 조회",
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
    @GetMapping("/album-sections")
    fun getMyPostsByAlbums(
        @Parameter(hidden = true) @AccountId accountId: Long,
        @PageableDefault pageable: Pageable
    ): ResponseEntity<GetMyPostsByAlbumsResponse.AlbumSections> {
        val response = getMyPostsByAlbumsUseCase.process(accountId, pageable)
        return ResponseEntity.ok(response)
    }
}
