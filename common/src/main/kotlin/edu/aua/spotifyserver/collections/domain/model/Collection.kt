package edu.aua.spotifyserver.collections.domain.model

import java.time.OffsetDateTime

class Collection(
    val id: String,
    val type: Type,
    val ownerId: String,
    var caption: String,
    var subCaption: String? = null,
    var displayImageUrl: String,
    var trackCount: Int,
    var duration: Long,
    var genres: Set<String>,
    var typeSpecificProperties: Map<String, Any?> = emptyMap(),
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    var updatedAt: OffsetDateTime = createdAt,
) {

    enum class Type {
        release,
        playlist,
        podcast;
    }
}
