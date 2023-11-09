package com.picpin.api.domains.post

import com.picpin.api.verticals.domain.exception.BusinessErrorCode
import com.picpin.api.verticals.domain.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository
) {

    fun save(post: Post): Post = postRepository.save(post)

    @Transactional
    fun modifyBy(post: TransientPost): Post {
        val targetPost = postRepository.findOneOrThrow(post.id)
        if (!targetPost.isOwner(post.writerId)) {
            throw BusinessException.from(BusinessErrorCode.THIS_ACCOUNT_IS_NOT_OWNER)
        }

        targetPost.update(post)
        return targetPost
    }
}
