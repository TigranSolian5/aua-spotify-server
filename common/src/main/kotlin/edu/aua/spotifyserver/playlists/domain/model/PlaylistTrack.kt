package edu.aua.spotifyserver.playlists.domain.model

import java.time.OffsetDateTime

class PlaylistTrack(
    val id: String,
    val caption: String,
    val artists: List<Artist>,
    val album: Album,
    val playback: PlaybackInfo,
    val addedAt: OffsetDateTime,
) {

    class PlaybackInfo(
        val url: String,
        val duration: Long,
    )

    class Artist(
        val id: String,
        val name: String,
    )

    class Album(
        val id: String,
        val caption: String,
        val displayImageUrl: String,
    )
}
