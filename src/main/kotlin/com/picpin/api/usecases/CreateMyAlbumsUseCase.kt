package com.picpin.api.usecases

import com.picpin.api.domains.album.AlbumService
import com.picpin.api.domains.post.PostService
import com.picpin.api.usecases.model.CreateMyAlbumCommand
import com.picpin.api.verticals.domain.transaction.TransactionHandler
import org.springframework.stereotype.Service

@Service
class CreateMyAlbumsUseCase(
    private val albumService: AlbumService,
    private val postService: PostService,
    private val transactionHandler: TransactionHandler
) {

    fun process(command: CreateMyAlbumCommand) {
        transactionHandler.runInTransaction {
            val album = albumService.save(command.toAlbum())
            postService.saveAllToAlbum(album.id, command.postIds)
        }
    }
}
