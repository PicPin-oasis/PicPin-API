package com.picpin.api.photo.usecases

import com.picpin.api.photo.domains.models.SearchType
import com.picpin.api.photo.domains.root.Photo
import com.picpin.api.photo.domains.root.PhotoService
import com.picpin.api.photo.interfaces.models.GetMyPhotosResponse
import com.picpin.api.verticals.stereotype.UseCase
import org.springframework.data.jpa.domain.Specification

@UseCase
class GetMyPhotosUseCase(
    private val photoService: PhotoService
) {

    operator fun invoke(rawSearchType: String?, accountId: Long): GetMyPhotosResponse.PhotoCards {
        val searchType: SearchType = SearchType.parse(rawSearchType)
        val specification = GetMyPhotosSpecification(searchType, accountId)

        val targetPhotos = photoService.readAllBySpecification(specification)
        return GetMyPhotosAssembler(targetPhotos)
    }
}

object GetMyPhotosAssembler {

    operator fun invoke(targetPhotos: List<Photo>): GetMyPhotosResponse.PhotoCards {
        val photoCards = targetPhotos.map {
            GetMyPhotosResponse.PhotoCard.create(it)
        }

        return GetMyPhotosResponse.PhotoCards(photoCards)
    }
}

object GetMyPhotosSpecification {

    operator fun invoke(searchType: SearchType, accountId: Long): Specification<Photo> {
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
