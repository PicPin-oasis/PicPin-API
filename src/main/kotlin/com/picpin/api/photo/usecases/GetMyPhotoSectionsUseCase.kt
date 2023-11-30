package com.picpin.api.photo.usecases

import com.picpin.api.photo.domains.root.Photo
import com.picpin.api.photo.domains.root.PhotoService
import com.picpin.api.photo.interfaces.models.GetMyPhotoSectionsResponse
import org.springframework.stereotype.Service

@Service
class GetMyPhotoSectionsUseCase(
    private val photoService: PhotoService
) {

    fun process(accountId: Long): GetMyPhotoSectionsResponse.PhotoSections {
        val targetPhotos = photoService.readAllByOwnerId(accountId)
        return GetMyPhotoSectionsAssembler.assemble(targetPhotos)
    }
}

object GetMyPhotoSectionsAssembler {

    fun assemble(photos: List<Photo>): GetMyPhotoSectionsResponse.PhotoSections {
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
