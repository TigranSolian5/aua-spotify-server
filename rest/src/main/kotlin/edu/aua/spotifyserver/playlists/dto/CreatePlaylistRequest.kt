package edu.aua.spotifyserver.playlists.dto

class CreatePlaylistRequest(
    val caption: String,
    val subCaption: String? = null,
    val displayImageUrl: String,
    val duration: Long,
    val genres: Set<String>,
    val public: Boolean,
    val tracks: List<String>,
)
