package com.picpin.api.post.usecases

import com.picpin.api.photo.domains.Photo
import com.picpin.api.photo.domains.PhotoService
import com.picpin.api.post.domains.root.Post
import com.picpin.api.post.domains.root.PostService
import com.picpin.api.post.interfaces.models.GetMyAllPostsResponse
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class GetMyAllPostsUseCase(
    private val postService: PostService,
    private val photoService: PhotoService
) {

    fun process(accountId: Long, onlyUnMapped: Boolean, pageable: Pageable): GetMyAllPostsResponse.Posts {
        val targetPosts = postService.findAllBy(accountId, onlyUnMapped, pageable)
        val targetPhotos = photoService.findAllBy(targetPosts.map { it.id })

        return GetMyAllPostsAssembler.assemble(targetPosts, targetPhotos)
    }
}

object GetMyAllPostsAssembler {

    fun assemble(posts: List<Post>, photos: List<Photo>): GetMyAllPostsResponse.Posts {
        val groupingPhotosByPostId = photos.groupBy { it.postId }
        val responses = posts.map { post ->
            val resolvedExposeImageUrl = groupingPhotosByPostId[post.id]?.first()?.imageUrl ?: ""
            GetMyAllPostsResponse.Post(post.id, post.title, resolvedExposeImageUrl)
        }

        return GetMyAllPostsResponse.Posts(responses)
    }
}
