package com.picpin.api.usecases

import com.picpin.api.domains.photo.Photo
import com.picpin.api.domains.photo.PhotoService
import com.picpin.api.domains.post.Post
import com.picpin.api.domains.post.PostService
import com.picpin.api.interfaces.model.GetMyPostResponse
import com.picpin.api.interfaces.model.GetMyPostsResponse
import org.springframework.stereotype.Service

@Service
class GetMyPostsUseCase(
    private val postService: PostService,
    private val photoService: PhotoService
) {

    fun process(accountId: Long, onlyUnMapped: Boolean): GetMyPostsResponse {
        val posts = postService.findAllBy(accountId, onlyUnMapped)
        val postIds = posts.map { it.id }

        val photos = photoService.findAllBy(postIds)
        val groupingPhotosByPostId = photos.groupBy { it.postId }
        val responses = posts.map {
            val resolvedExposeImageUrl = resolveExposeImageUrl(groupingPhotosByPostId, it)
            GetMyPostResponse(it.id, it.title, resolvedExposeImageUrl)
        }
        return GetMyPostsResponse(responses)
    }

    private fun resolveExposeImageUrl(
        photoByPostIdMap: Map<Long, List<Photo>>,
        it: Post
    ) = photoByPostIdMap[it.id]?.first()?.imageUrl ?: ""
}
