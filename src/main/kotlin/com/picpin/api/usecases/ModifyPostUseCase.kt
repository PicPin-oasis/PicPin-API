package com.picpin.api.usecases

import com.picpin.api.domains.album.AlbumService
import com.picpin.api.domains.photo.PhotoService
import com.picpin.api.domains.post.PostService
import com.picpin.api.domains.post.coordinate.PostCoordinateService
import com.picpin.api.usecases.model.ModifyPostCommand
import com.picpin.api.verticals.domain.transaction.TransactionHandler
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
            photoService.saveAll(command.toPhotos())

            if (command.ifModifyPostCoordinate()) {
                val postCoordinate = command.toPostCoordinate(targetPost.postCoordinateId)
                postCoordinateService.modifyBy(postCoordinate)
            }
        }
    }
}
