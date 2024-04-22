package edu.aua.spotifyserver.playlists

import edu.aua.spotifyserver.common.ResponseMetadata
import edu.aua.spotifyserver.common.SuccessResponse
import edu.aua.spotifyserver.common.nextPageWithParams
import edu.aua.spotifyserver.playlists.domain.GetPlaylistForDisplayUseCase
import edu.aua.spotifyserver.playlists.dto.DisplayPlaylistResponse
import edu.aua.spotifyserver.playlists.dto.PlaylistTrackDto
import edu.aua.spotifyserver.playlists.dto.mappers.PlaylistMapper
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

private const val DEFAULT_TRACK_OFFSET = 0
private const val DEFAULT_TRACK_LIMIT = 100

@RestController
@Tag(name = "playlists public", description = "Public operations for playlists")
class GetPlaylistController(
    private val useCase: GetPlaylistForDisplayUseCase,
) {

    @GetMapping("/v1/playlists/{id}/display")
    suspend fun display(
        @PathVariable("id") id: String,
    ): SuccessResponse<DisplayPlaylistResponse> {
        val playlist = useCase.getById(id, DEFAULT_TRACK_OFFSET, DEFAULT_TRACK_LIMIT)

        val hasNextPage = playlist.tracks.size >= DEFAULT_TRACK_LIMIT
        var nextPageUrl: String? = null
        if (hasNextPage) {
            nextPageUrl = nextPageWithParams(
                uri = "{baseApiUrl}/v1/playlists/$id/tracks",
                query = mapOf(
                    "offset" to playlist.tracks.size,
                    "limit" to DEFAULT_TRACK_LIMIT,
                )
            )
        }

        return SuccessResponse(
            response = PlaylistMapper.INSTANCE.domainToResponse(playlist),
            metadata = ResponseMetadata(
                nextPageUrl = nextPageUrl,
            ),
        )
    }

    @GetMapping("/v1/playlists/{id}/tracks")
    suspend fun getTracks(
        @PathVariable("id") id: String,
        @RequestParam("offset", required = false) offset: Int = 0,
        @RequestParam("limit", required = false) limit: Int = DEFAULT_TRACK_LIMIT,
    ): SuccessResponse<List<PlaylistTrackDto>> {
        val tracks = useCase.getPlaylistTracks(id, offset, limit)

        val hasNextPage = tracks.size >= DEFAULT_TRACK_LIMIT
        var nextPageUrl: String? = null
        if (hasNextPage) {
            nextPageUrl = nextPageWithParams(
                uri = "{baseApiUrl}/v1/playlists/$id/tracks",
                query = mapOf(
                    "offset" to (offset + tracks.size),
                    "limit" to DEFAULT_TRACK_LIMIT,
                )
            )
        }


        return SuccessResponse(
            response = tracks.map { PlaylistMapper.INSTANCE.domainToResponse(it) },
            metadata = ResponseMetadata(
                nextPageUrl = nextPageUrl,
            ),
        )
    }
}
