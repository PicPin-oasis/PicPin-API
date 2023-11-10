package com.picpin.api.usecases

import com.picpin.api.domains.photo.Photo
import com.picpin.api.domains.photo.PhotoService
import com.picpin.api.domains.post.Post
import com.picpin.api.domains.post.PostService
import com.picpin.api.interfaces.model.GetMyPostsByDatesResponse
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class GetMyPostsByDateUseCase(
    private val postService: PostService,
    private val photoService: PhotoService
) {

    fun process(accountId: Long, pageable: Pageable): GetMyPostsByDatesResponse.PostSections {
        val targetPosts = postService.findAllBy(accountId, pageable)
        val postIds = targetPosts.map { it.id }

        val targetPhotos = photoService.findAllBy(postIds)
        val groupingPhotoByPostId = targetPhotos.groupBy { it.postId }
        val groupingPostByTakenPhotoDate = targetPosts.groupBy { it.takenPhotoDate }

        val postSections = createPostSections(groupingPostByTakenPhotoDate, groupingPhotoByPostId)
        return GetMyPostsByDatesResponse.PostSections(postSections)
    }

    private fun createPostSections(
        groupingPostByTakenPhotoDate: Map<LocalDate, List<Post>>,
        groupingPhotoByPostId: Map<Long, List<Photo>>
    ): List<GetMyPostsByDatesResponse.PostSection> =
        groupingPostByTakenPhotoDate.entries.map { (takenPhotoDate, posts) ->
            createPostSection(posts, groupingPhotoByPostId, takenPhotoDate)
        }.filter { it.postCards.isNotEmpty() }

    private fun createPostSection(
        posts: List<Post>,
        groupingPhotoByPostId: Map<Long, List<Photo>>,
        takenPhotoDate: LocalDate
    ): GetMyPostsByDatesResponse.PostSection {
        val sortedPostCards = posts.map {
            GetMyPostsByDatesResponse.PostCard.create(it, groupingPhotoByPostId)
        }.sortedBy { it.title }
        return GetMyPostsByDatesResponse.PostSection(takenPhotoDate, sortedPostCards)
    }
}
