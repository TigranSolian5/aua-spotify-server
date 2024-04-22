package edu.aua.spotifyserver.genres.mongodb.model

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "genres")
class GenreDocument(
    @field:Indexed(unique = true)
    val key: String,
    val parentKey: String?,
    val displayName: String,
    val description: String?,
)
