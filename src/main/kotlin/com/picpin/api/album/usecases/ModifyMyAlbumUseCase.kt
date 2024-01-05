package com.picpin.api.album.usecases

import com.picpin.api.album.domains.AlbumService
import org.springframework.stereotype.Service

@Service
class ModifyMyAlbumUseCase(
    private val albumService: AlbumService
) {

    operator fun invoke(command: ModifyMyAlbumCommand) {
        albumService.modifyBy(command.toAlbum())
    }
}
