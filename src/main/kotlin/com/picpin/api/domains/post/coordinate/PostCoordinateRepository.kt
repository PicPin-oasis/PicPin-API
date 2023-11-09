package com.picpin.api.domains.post.coordinate

import com.picpin.api.verticals.domain.exception.BusinessErrorCode
import com.picpin.api.verticals.domain.exception.BusinessException
import org.springframework.data.jpa.repository.JpaRepository

interface PostCoordinateRepository : JpaRepository<PostCoordinate, Long>

fun PostCoordinateRepository.findBy(postCoordinateId: Long): PostCoordinate {
    return findById(postCoordinateId).orElseThrow {
        BusinessException.from(BusinessErrorCode.POST_COORDINATE_NOT_FOUND)
    }
}
