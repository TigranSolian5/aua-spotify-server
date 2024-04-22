package edu.aua.spotifyserver.tracks.domain.model.props

class CreateTrackProps(
    val artistIds: List<String>,
    val collectionId: String,
    val trackUrl: String,
    val duration: Long,
    val caption: String,
    val genres: Set<String>,
)
