package com.picpin.api.photo.usecases

import com.picpin.api.photo.domains.models.SearchType
import com.picpin.api.photo.domains.root.Photo
import com.picpin.api.photo.domains.root.PhotoService
import com.picpin.api.photo.interfaces.models.GetMyPhotosResponse
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

@Service
class GetMyPhotosUseCase(
    private val photoService: PhotoService
) {

    fun process(rawSearchType: String?, accountId: Long): GetMyPhotosResponse.PhotoCards {
        val searchType: SearchType = SearchType.parse(rawSearchType)
        val specification = GetMyPhotosSpec.build(searchType, accountId)

        val targetPhotos = photoService.readAllBySpecification(specification)
        return GetMyPhotosAssembler.assemble(targetPhotos)
    }
}

object GetMyPhotosAssembler {

    fun assemble(targetPhotos: List<Photo>): GetMyPhotosResponse.PhotoCards {
        val photoCards = targetPhotos.map {
            GetMyPhotosResponse.PhotoCard.create(it)
        }

        return GetMyPhotosResponse.PhotoCards(photoCards)
    }
}

object GetMyPhotosSpec {

    fun build(searchType: SearchType, accountId: Long): Specification<Photo> {
        return Specification<Photo> { root, _, builder ->
            when (searchType) {
                SearchType.ALL -> {
                    builder.equal(root.get<Any>("ownerId"), accountId)
                }
                SearchType.MAPPING -> {
                    builder.and(
                        builder.isNotNull(root.get<Any>("albumId")),
                        builder.equal(root.get<Any>("ownerId"), accountId)
                    )
                }
                SearchType.UN_MAPPING -> {
                    builder.and(
                        builder.isNull(root.get<Any>("albumId")),
                        builder.equal(root.get<Any>("ownerId"), accountId)
                    )
                }
            }
        }
    }
}
