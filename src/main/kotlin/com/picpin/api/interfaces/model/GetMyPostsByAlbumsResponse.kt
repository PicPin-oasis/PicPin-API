package com.picpin.api.interfaces.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.picpin.api.domains.photo.Photo
import com.picpin.api.domains.post.Post

class GetMyPostsByAlbumsResponse {
    data class AlbumSections(
        @JsonProperty("album_sections")
        val albumSections: List<AlbumSection>
    )

    data class AlbumSection(
        val title: String,
        @JsonProperty("post_cards")
        val postCards: List<PostCard>
    )

    data class PostCard(
        val id: Long,
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
