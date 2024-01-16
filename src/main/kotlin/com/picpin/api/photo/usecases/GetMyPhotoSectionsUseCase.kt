package com.picpin.api.photo.usecases

import com.picpin.api.photo.domains.root.Photo
import com.picpin.api.photo.domains.root.PhotoService
import com.picpin.api.photo.interfaces.models.GetMyPhotoSectionsResponse
import com.picpin.api.verticals.stereotype.UseCase

@UseCase
class GetMyPhotoSectionsUseCase(
    private val photoService: PhotoService
) {

    operator fun invoke(accountId: Long): GetMyPhotoSectionsResponse.PhotoSections {
        val targetPhotos = photoService.findAllByOwnerId(accountId)
        return GetMyPhotoSectionsAssembler(targetPhotos)
    }
}

object GetMyPhotoSectionsAssembler {

    operator fun invoke(photos: List<Photo>): GetMyPhotoSectionsResponse.PhotoSections {
        val groupingPhotosByTakenPhotoDate = photos.groupBy { it.takenPhotoDate }

        val responses = groupingPhotosByTakenPhotoDate.entries.map { (takenPhotoDate, photos) ->
            val photoCards = photos.map {
                GetMyPhotoSectionsResponse.PhotoCard.create(it)
            }

            GetMyPhotoSectionsResponse.PhotoSection(takenPhotoDate, photoCards)
        }

        val filteredResponses = responses.sortedByDescending { it.takenPhotoDate }
        return GetMyPhotoSectionsResponse.PhotoSections(filteredResponses)
    }
}
