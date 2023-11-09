package com.picpin.api.domains.post

import com.picpin.api.verticals.domain.exception.BusinessErrorCode
import com.picpin.api.verticals.domain.exception.BusinessException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long> {
    fun findAllByWriterId(writerId: Long): List<Post>
}

fun PostRepository.findOneOrThrow(postId: Long): Post {
    return findById(postId).orElseThrow {
        BusinessException.of(BusinessErrorCode.POST_NOT_FOUND, "postId = $postId")
    }
}
