package com.picpin.api.post.usecases

import com.picpin.api.photo.domains.Photo
import com.picpin.api.photo.domains.PhotoService
import com.picpin.api.post.domains.root.Post
import com.picpin.api.post.domains.root.PostService
import com.picpin.api.post.interfaces.models.GetMyPostsByDatesResponse
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class GetMyPostsByDateUseCase(
    private val postService: PostService,
    private val photoService: PhotoService
) {

    fun process(accountId: Long, pageable: Pageable): GetMyPostsByDatesResponse.PostSections {
        val targetPosts = postService.findAllBy(accountId, pageable)
        val targetPhotos = photoService.findAllBy(targetPosts.map { it.id })

        return GetMyPostsByDateAssembler.assemble(targetPosts, targetPhotos)
    }
}

object GetMyPostsByDateAssembler {

    fun assemble(posts: List<Post>, photos: List<Photo>): GetMyPostsByDatesResponse.PostSections {
        val groupingPostByTakenPhotoDate = posts.groupBy { it.takenPhotoDate }
        val groupingPhotoByPostId = photos.groupBy { it.postId }

        val responses = groupingPostByTakenPhotoDate.entries.map { (takenPhotoDate, posts) ->
            val postCards = posts.map {
                GetMyPostsByDatesResponse.PostCard.create(it, groupingPhotoByPostId)
            }

            val sortedPostCards = postCards.sortedBy { it.title }
            GetMyPostsByDatesResponse.PostSection(takenPhotoDate, sortedPostCards)
        }

        val filteredResponses = responses.filter { it.postCards.isNotEmpty() }
        return GetMyPostsByDatesResponse.PostSections(filteredResponses)
    }
}
