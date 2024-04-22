package edu.aua.spotifyserver.tracks.persistence.mongodb.model

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import java.time.OffsetDateTime

@Document(collection = "tracks")
class TrackDocument(
    @field:MongoId
    val id: String?,
    val artistIds: List<String>,
    val collectionId: String,
    val trackUrl: String,
    val duration: Long,
    val caption: String,
    val genres: Set<String>,
    val createdAt: OffsetDateTime,
)
