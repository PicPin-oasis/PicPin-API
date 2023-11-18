package com.picpin.api.post.usecases

import com.picpin.api.album.domains.Album
import com.picpin.api.album.domains.AlbumService
import com.picpin.api.photo.domains.Photo
import com.picpin.api.photo.domains.PhotoService
import com.picpin.api.post.domains.root.Post
import com.picpin.api.post.domains.root.PostService
import com.picpin.api.post.interfaces.models.GetMyPostsByAlbumsResponse
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class GetMyPostsByAlbumsUseCase(
    private val postService: PostService,
    private val photoService: PhotoService,
    private val albumService: AlbumService
) {

    fun process(accountId: Long, pageable: Pageable): GetMyPostsByAlbumsResponse.AlbumSections {
        val targetAlbums = albumService.findAllByOwnerId(accountId, pageable)
        val targetPost = postService.findAllByAlbumIds(targetAlbums.map { it.id })
        val targetPhotos = photoService.findAllBy(targetPost.map { it.id })

        return GetMyPostsByAlbumsAssembler.assemble(targetAlbums, targetPost, targetPhotos)
    }
}

object GetMyPostsByAlbumsAssembler {

    fun assemble(
        albums: List<Album>,
        posts: List<Post>,
        photos: List<Photo>
    ): GetMyPostsByAlbumsResponse.AlbumSections {
        val groupingAlbumById = albums.associateBy { it.id }
        val groupingPostByAlbumId = posts.groupBy { it.albumId!! }
        val groupingPhotoByPostId = photos.groupBy { it.postId }
        val responses = groupingPostByAlbumId.entries.map { (albumId, posts) ->
            val sortedPostCards = posts
                .map { GetMyPostsByAlbumsResponse.PostCard.create(it, groupingPhotoByPostId) }
                .sortedBy { it.title }

            val album = groupingAlbumById[albumId]!!
            GetMyPostsByAlbumsResponse.AlbumSection(album.title, sortedPostCards)
        }

        val sortedResponses = responses.sortedBy { it.title }
        return GetMyPostsByAlbumsResponse.AlbumSections(sortedResponses)
    }
}
