package com.picpin.api.domains.post.coordinate

import com.picpin.api.verticals.domain.exception.BusinessErrorCode
import com.picpin.api.verticals.domain.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostCoordinateService(
    private val postCoordinateRepository: PostCoordinateRepository
) {

    fun save(postCoordinate: PostCoordinate): PostCoordinate =
        postCoordinateRepository.save(postCoordinate)

    @Transactional
    fun modifyBy(postCoordinate: TransientPostCoordinate): PostCoordinate {
        val targetPostCoordinate = postCoordinateRepository.findBy(postCoordinate.id)
        if (targetPostCoordinate.isOwner(postCoordinate.accountId)) {
            throw BusinessException.from(BusinessErrorCode.THIS_ACCOUNT_IS_NOT_OWNER)
        }

        targetPostCoordinate.update(postCoordinate)
        return targetPostCoordinate
    }
}
