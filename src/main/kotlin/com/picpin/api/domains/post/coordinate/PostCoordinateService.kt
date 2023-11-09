package com.picpin.api.domains.post.coordinate

import org.springframework.stereotype.Service

@Service
class PostCoordinateService(
    private val postCoordinateRepository: PostCoordinateRepository
) {

    fun save(postCoordinate: PostCoordinate): PostCoordinate =
        postCoordinateRepository.save(postCoordinate)
}
