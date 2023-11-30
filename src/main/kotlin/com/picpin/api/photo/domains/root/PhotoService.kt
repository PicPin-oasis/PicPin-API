package com.picpin.api.photo.domains.root

import com.picpin.api.verticals.domains.exception.BusinessErrorCode
import com.picpin.api.verticals.domains.exception.BusinessException
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PhotoService(
    private val photoRepository: PhotoRepository
) {

    fun saveAll(photos: List<Photo>) {
        photoRepository.saveAll(photos)
    }

    fun readAllByOwnerId(ownerId: Long): List<Photo> {
        return photoRepository.findAllByOwnerId(ownerId)
    }

    fun readByOwnerId(photoId: Long, ownerId: Long): Photo {
        val targetPhoto = photoRepository.findOneOrThrow(photoId)
        if (targetPhoto.isOwner(ownerId).not()) {
            throw BusinessException.from(BusinessErrorCode.PHOTO_NOT_FOUND)
        }
        return targetPhoto
    }

    fun readAllBySpecification(specification: Specification<Photo>): List<Photo> {
        return photoRepository.findAll(specification)
    }

    @Transactional
    fun modifyBy(photo: Photo): Photo {
        val targetPhoto = photoRepository.findOneOrThrow(photo.id)
        if (targetPhoto.isOwner(photo.ownerId).not()) {
            throw BusinessException.from(BusinessErrorCode.THIS_ACCOUNT_IS_NOT_OWNER)
        }

        targetPhoto.update(photo)
        return targetPhoto
    }

    @Transactional
    fun removeBy(photoId: Long, ownerId: Long) {
        photoRepository.deleteByIdAndOwnerId(photoId, ownerId)
    }
}
