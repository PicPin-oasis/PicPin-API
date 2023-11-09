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

    fun saveAllToAlbum(albumId: Long, postIds: List<Long>) {
        val uniquePostIds = postIds.toSet()
        val targetPosts = postRepository.findAllById(uniquePostIds)
        if (targetPosts.size != uniquePostIds.size) {
            val targetPostMap = targetPosts.associateBy { it.id }
            val unsavedUniqueIds = uniquePostIds.filter { targetPostMap[it] == null }

            throw BusinessException.of(
                BusinessErrorCode.CONTAINS_UNSAVED_POST_IDS,
                "unsaved ids = $unsavedUniqueIds"
            )
        }

        val alreadyStoredInAlbumPosts = targetPosts.filter { it.albumId != null }
        if (alreadyStoredInAlbumPosts.isNotEmpty()) {
            throw BusinessException.from(BusinessErrorCode.ALREADY_STORED_IN_ALBUM_POST)
        }

        targetPosts.forEach { it.albumId = albumId }
        postRepository.saveAll(targetPosts)
    }
}
