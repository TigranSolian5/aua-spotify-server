package edu.aua.spotifyserver.collections.domain.model.props

import edu.aua.spotifyserver.collections.domain.model.Collection
import java.time.OffsetDateTime

class CreateCollectionProps(
    val type: Collection.Type,
    val ownerId: String,
    val caption: String,
    val subCaption: String? = null,
    val displayImageUrl: String,
    var trackCount: Int = 0,
    val duration: Long,
    val genres: Set<String>,
    var typeSpecificProperties: Map<String, Any?> = emptyMap(),
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    val updatedAt: OffsetDateTime = createdAt,
)
