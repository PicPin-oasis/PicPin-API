package com.picpin.api.interfaces.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.picpin.api.domains.photo.Photo
import com.picpin.api.domains.post.Post
import java.time.LocalDate

class GetMyPostsByDatesResponse {
    data class PostSections(
        @JsonProperty("post_sections")
        val postSections: List<PostSection>
    )

    data class PostSection(
        @JsonProperty("taken_photo_date")
        val takenPhotoDate: LocalDate,
        @JsonProperty("post_cards")
        val postCards: List<PostCard>
    )

    data class PostCard(
        val id: Long,
        @JsonProperty("title")
        val title: String,
        @JsonProperty("expose_image_url")
        val exposeImageUrl: String,
        @JsonProperty("more_image_count")
        val moreImageCount: Int
    ) {
        companion object {
            fun create(post: Post, groupingPhotoByPostId: Map<Long, List<Photo>>): PostCard {
                val photosByPostId = groupingPhotoByPostId[post.id]
                val exposeImageUrl = photosByPostId?.first()?.imageUrl ?: ""
                val imageCount = photosByPostId?.size ?: 0

                val moreImageCount = resolveImageCount(imageCount)
                return PostCard(post.id, post.title, exposeImageUrl, moreImageCount)
            }

            private fun resolveImageCount(imageCount: Int) = if (imageCount >= 1) {
                imageCount.minus(1)
            } else {
                0
            }
        }
    }
}
