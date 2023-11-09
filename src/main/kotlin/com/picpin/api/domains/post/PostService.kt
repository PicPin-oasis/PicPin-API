package com.picpin.api.domains.post

import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository
) {

    fun save(post: Post): Post = postRepository.save(post)
}
