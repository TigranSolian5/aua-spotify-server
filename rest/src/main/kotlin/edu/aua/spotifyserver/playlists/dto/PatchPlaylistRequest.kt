package edu.aua.spotifyserver.playlists.dto

import edu.aua.spotifyserver.tracks.dto.TrackInfoDto

class PatchPlaylistRequest(
    val caption: String?,
    val subCaption: String?,
    val displayImageUrl: String?,
    val public: Boolean?,
    val tracks: List<TrackInfoDto>,

    val addTracks: List<String>?,
    val deleteTracks: List<String>?,
    val addGenres: List<String>?,
    val deleteGenres: List<String>?,
)
