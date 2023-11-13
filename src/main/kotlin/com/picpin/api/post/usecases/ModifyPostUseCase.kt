package com.picpin.api.post.usecases

import com.picpin.api.album.domains.AlbumService
import com.picpin.api.photo.domains.PhotoService
import com.picpin.api.post.domains.coordinate.PostCoordinateService
import com.picpin.api.post.domains.root.PostService
import com.picpin.api.post.usecases.model.ModifyPostCommand
import com.picpin.api.verticals.domains.database.TransactionHandler
import org.springframework.stereotype.Service

@Service
class ModifyPostUseCase(
    private val albumService: AlbumService,
    private val postCoordinateService: PostCoordinateService,
    private val postService: PostService,
    private val photoService: PhotoService,
    private val transactionHandler: TransactionHandler
) {

    fun process(command: ModifyPostCommand) {
        if (command.ifAddToOnAlbum()) {
            albumService.findOneOrThrow(command.albumId!!)
        }

        transactionHandler.runInTransaction {
            val targetPost = postService.modifyBy(command.toPost())
            photoService.saveAll(command.toNewPhotos())
            photoService.deleteAll(command.toDeletedPhotos())

            if (command.ifModifyPostCoordinate()) {
                val postCoordinate = command.toPostCoordinate(targetPost.postCoordinateId)
                postCoordinateService.modifyBy(postCoordinate)
            }
        }
    }
}
