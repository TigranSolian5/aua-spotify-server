package edu.aua.spotifyserver.playlists.dto

import java.time.OffsetDateTime

// TODO unify with other track dtos
class PlaylistTrackDto(
    val id: String,
    val caption: String,
    val artists: List<ArtistDto>,
    val album: AlbumDto,
    val playback: PlaybackInfoDto,
    val addedAt: OffsetDateTime,
) {

    class PlaybackInfoDto(
        val url: String,
        val duration: Long,
    )

    class ArtistDto(
        val id: String,
        val name: String,
    )

    class AlbumDto(
        val id: String,
        val caption: String,
        val displayImageUrl: String,
    )
}
