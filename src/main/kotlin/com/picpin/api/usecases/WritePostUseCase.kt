package com.picpin.api.usecases

import com.picpin.api.domains.album.AlbumService
import com.picpin.api.domains.photo.PhotoService
import com.picpin.api.domains.post.PostService
import com.picpin.api.domains.post.coordinate.PostCoordinateService
import com.picpin.api.usecases.model.WritePostCommand
import com.picpin.api.verticals.domain.transaction.TransactionHandler
import org.springframework.stereotype.Service

@Service
class WritePostUseCase(
    private val albumService: AlbumService,
    private val postCoordinateService: PostCoordinateService,
    private val postService: PostService,
    private val photoService: PhotoService,
    private val transactionHandler: TransactionHandler
) {

    fun process(command: WritePostCommand) {
        if (command.ifAddToOnAlbum()) {
            albumService.findOneOrThrow(command.albumId!!)
        }

        transactionHandler.runInTransaction {
            val postCoordinate = postCoordinateService.save(command.toPostCoordinate())
            val post = postService.save(command.toPost(postCoordinate.id))
            photoService.saveAll(command.toPhotos(post.id))
        }
    }
}
