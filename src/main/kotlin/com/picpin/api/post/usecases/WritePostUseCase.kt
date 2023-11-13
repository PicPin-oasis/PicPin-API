package com.picpin.api.post.usecases

import com.picpin.api.album.domains.AlbumService
import com.picpin.api.photo.domains.PhotoService
import com.picpin.api.post.domains.coordinate.PostCoordinateService
import com.picpin.api.post.domains.root.PostService
import com.picpin.api.post.usecases.model.WritePostCommand
import com.picpin.api.verticals.domains.database.TransactionHandler
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
