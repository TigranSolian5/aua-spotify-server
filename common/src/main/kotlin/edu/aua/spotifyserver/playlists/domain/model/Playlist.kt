package edu.aua.spotifyserver.playlists.domain.model

import java.time.OffsetDateTime

class Playlist(
    val id: String,
    val public: Boolean,
    val owner: Owner,
    val caption: String,
    val subCaption: String?,
    val displayImageUrl: String,
    val trackCount: Int,
    val duration: Long,
    val genres: Set<String>,
    val likeCount: Int,
    val tracks: List<PlaylistTrack> = emptyList(),
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime,
) {

    class Owner(
        val id: String,
        val name: String?,
        val profileImageUrl: String,
    )
}
