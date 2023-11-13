package com.picpin.api.album.usecases

import com.picpin.api.album.domains.AlbumService
import com.picpin.api.post.domains.root.PostService
import com.picpin.api.verticals.domains.database.TransactionHandler
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
