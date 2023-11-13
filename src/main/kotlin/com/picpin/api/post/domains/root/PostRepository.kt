package com.picpin.api.post.domains.root

import com.picpin.api.verticals.domains.exception.BusinessErrorCode
import com.picpin.api.verticals.domains.exception.BusinessException
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long> {
    fun findAllByWriterId(writerId: Long, pageable: Pageable): List<Post>

    fun findAllByAlbumIdIn(albumIds: List<Long>): List<Post>
}

fun PostRepository.findOneOrThrow(postId: Long): Post {
    return findById(postId).orElseThrow {
        BusinessException.of(BusinessErrorCode.POST_NOT_FOUND, "postId = $postId")
    }
}
