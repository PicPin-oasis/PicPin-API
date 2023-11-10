package com.picpin.api.interfaces.model

class GetMyAllPosts {
    data class Posts(
        val posts: List<Post>
    )

    data class Post(
        val id: Long,
        val title: String,
        val expose_image_url: String
    )
}
