package com.picpin.api.album.usecases

import com.picpin.api.album.domains.AlbumService
import com.picpin.api.verticals.domains.database.TransactionHandler
import com.picpin.api.verticals.stereotype.UseCase

@UseCase
class CreateMyAlbumsUseCase(
    private val albumService: AlbumService,
    private val transactionHandler: TransactionHandler
) {

    operator fun invoke(command: CreateMyAlbumCommand) {
        transactionHandler.runInTransaction {
            val album = albumService.save(command.toAlbum())
            command.postIds
        }
    }
}
