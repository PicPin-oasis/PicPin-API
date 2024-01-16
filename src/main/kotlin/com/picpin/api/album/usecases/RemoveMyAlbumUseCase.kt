package com.picpin.api.album.usecases

import com.picpin.api.album.domains.AlbumService
import com.picpin.api.photo.domains.root.PhotoService
import com.picpin.api.verticals.domains.database.TransactionHandler
import org.springframework.stereotype.Service

@Service
class RemoveMyAlbumUseCase(
    private val albumService: AlbumService,
    private val photoService: PhotoService,
    private val transactionHandler: TransactionHandler
) {

    operator fun invoke(accountId: Long, albumId: Long) {
        transactionHandler.runInTransaction {
            albumService.removeBy(albumId, accountId)
            photoService.findAllByAlbumId(albumId)
        }
    }
}
