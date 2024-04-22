package edu.aua.spotifyserver.genres.domain.model

class Genre(
    val key: String,
    var parentKey: String?,
    var displayName: String,
    var description: String? = null,
)
