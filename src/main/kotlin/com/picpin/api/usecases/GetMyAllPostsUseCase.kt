package com.picpin.api.usecases

import com.picpin.api.domains.photo.Photo
import com.picpin.api.domains.photo.PhotoService
import com.picpin.api.domains.post.Post
import com.picpin.api.domains.post.PostService
import com.picpin.api.interfaces.model.GetMyAllPosts
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class GetMyAllPostsUseCase(
    private val postService: PostService,
    private val photoService: PhotoService
) {

    fun process(accountId: Long, onlyUnMapped: Boolean, pageable: Pageable): GetMyAllPosts.Posts {
        val targetPosts = postService.findAllBy(accountId, onlyUnMapped, pageable)
        val postIds = targetPosts.map { it.id }

        val targetPhotos = photoService.findAllBy(postIds)
        val groupingPhotosByPostId = targetPhotos.groupBy { it.postId }

        val posts = createPosts(targetPosts, groupingPhotosByPostId)
        return GetMyAllPosts.Posts(posts)
    }

    private fun createPosts(
        targetPosts: List<Post>,
        groupingPhotosByPostId: Map<Long, List<Photo>>
    ) = targetPosts.map {
        val resolvedExposeImageUrl = resolveExposeImageUrl(groupingPhotosByPostId, it)
        GetMyAllPosts.Post(it.id, it.title, resolvedExposeImageUrl)
    }

    private fun resolveExposeImageUrl(
        photoByPostIdMap: Map<Long, List<Photo>>,
        it: Post
    ) = photoByPostIdMap[it.id]?.first()?.imageUrl ?: ""
}
