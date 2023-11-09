package com.picpin.api.interfaces.model

data class GetMyPostsResponse(val posts: List<GetMyPostResponse>)

data class GetMyPostResponse(
    val id: Long,
    val title: String,
    val expose_image_url: String
)
