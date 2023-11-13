package com.picpin.api.photo.domains

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PhotoService(
    private val photoRepository: PhotoRepository
) {

    fun saveAll(photos: List<Photo>) {
        photoRepository.saveAll(photos)
    }

    fun deleteAll(photos: List<Photo>) {
        photoRepository.deleteAll(photos)
    }

    @Transactional(readOnly = true)
    fun findAllBy(postIds: List<Long>): List<Photo> = photoRepository.findAllByPostIdIn(postIds)
}
