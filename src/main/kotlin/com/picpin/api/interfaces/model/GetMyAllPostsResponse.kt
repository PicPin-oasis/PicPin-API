package com.picpin.api.interfaces.model

import com.fasterxml.jackson.annotation.JsonProperty

class GetMyAllPostsResponse {
    data class Posts(
        val posts: List<Post>
    )

    data class Post(
        val id: Long,
        val title: String,
        @JsonProperty("expose_image_url")
        val exposeImageUrl: String
    )
}
