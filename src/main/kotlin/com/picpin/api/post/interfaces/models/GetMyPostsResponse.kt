package com.picpin.api.post.interfaces.models

data class GetMyPostsResponse(val posts: List<GetMyPostResponse>)

data class GetMyPostResponse(
    val id: Long,
    val title: String,
    val expose_image_url: String
)
