package edu.aua.spotifyserver.playlists

import edu.aua.spotifyserver.playlists.domain.PatchPlaylistUseCase
import edu.aua.spotifyserver.playlists.dto.PatchPlaylistRequest
import edu.aua.spotifyserver.playlists.dto.mappers.PlaylistMapper
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "playlists public", description = "Public operations for playlists")
class PatchPlaylistController(
    private val useCase: PatchPlaylistUseCase,
) {

    @PatchMapping("/v1/playlists/{playlistId}")
    suspend fun patch(
        authentication: Authentication,
        @PathVariable("playlistId") playlistId: String,
        @RequestBody request: PatchPlaylistRequest,
    ): ResponseEntity<Unit> {
        val userId = authentication.principal as String
        val props = PlaylistMapper.INSTANCE.requestToProps(request)
        useCase.patch(playlistId, userId, props)

        return ResponseEntity.noContent().build()
    }
}
