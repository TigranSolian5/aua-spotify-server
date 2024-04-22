package edu.aua.spotifyserver.playlists.domain.model

import java.time.OffsetDateTime

class PatchPlaylistProps(
    val caption: String? = null,
    val subCaption: String? = null,
    val displayImageUrl: String? = null,
    val public: Boolean? = null,
    val tracks: List<TrackInfo> = emptyList(),

    val addTracks: List<String> = emptyList(),
    val deleteTracks: List<String> = emptyList(),
    val addGenres: Set<String> = emptySet(),
    val deleteGenres: Set<String> = emptySet(),
) {

    class TrackInfo(
        val id: String,
        val addedAt: OffsetDateTime? = null,
    )
}
