package edu.aua.spotifyserver.playlists

import edu.aua.spotifyserver.collections.domain.model.Collection
import edu.aua.spotifyserver.collections.domain.model.props.CreateCollectionProps
import edu.aua.spotifyserver.collections.domain.usecase.CreateCollectionUseCase
import edu.aua.spotifyserver.playlists.dto.CreatePlaylistRequest
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import java.time.OffsetDateTime

@RestController
@Tag(name = "playlists public", description = "Public operations for playlists")
class CreatePlaylistController(
    private val createCollectionUseCase: CreateCollectionUseCase,
) {

    @PostMapping("/v1/playlists")
    suspend fun create(
        authentication: Authentication,
        @RequestBody request: CreatePlaylistRequest,
    ): ResponseEntity<String> {
        val userId = authentication.principal as String

        val props = with(request) {
            val now = OffsetDateTime.now()
            CreateCollectionProps(
                type = Collection.Type.playlist,
                ownerId = userId,
                caption = caption,
                subCaption = subCaption,
                displayImageUrl = displayImageUrl,
                duration = duration,
                genres = genres,
                typeSpecificProperties = mapOf(
                    "public" to public,
                    "likeCount" to 0,
                    "tracks" to tracks.map { mapOf("id" to it, "addedAt" to now) },
                ),
                createdAt = now,
                updatedAt = now,
            )
        }
        val id = createCollectionUseCase.create(props).id

        val uriComponents = UriComponentsBuilder.fromPath("/v1/playlists/{id}").buildAndExpand(id)
        return ResponseEntity.created(uriComponents.toUri()).body(id)
    }
}
