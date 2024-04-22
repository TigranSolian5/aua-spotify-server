package edu.aua.spotifyserver.tracks.domain.model

import java.time.OffsetDateTime

class Track(
    val id: String,
    var artistIds: List<String>,
    var collectionId: String,
    var trackUrl: String,
    var duration: Long,
    var caption: String,
    var genres: Set<String>,
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
)
