package edu.aua.spotifyserver.collections.persistence.mongodb.model

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import java.time.OffsetDateTime

@Document(collection = "collections")
class CollectionDocument(
    @field:MongoId
    val id: String,
    val type: String,
    @field:Indexed
    val ownerId: String,
    val caption: String,
    val subCaption: String? = null,
    val displayImageUrl: String,
    val trackCount: Int,
    val duration: Long,
    val genres: Set<String>,
    val typeSpecificProperties: Map<String, Any?>,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime,
)
