package edu.aua.spotifyserver.collections.domain.model.specific

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.time.OffsetDateTime

class PlaylistTypeSpecificProperties(
    var public: Boolean,
    var likeCount: Int,
    var tracks: List<TrackInfo>,
) {

    companion object {
        private val objectMapper = jacksonObjectMapper()
            .registerModule(JavaTimeModule())
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)

        fun fromMap(map: Map<String, Any?>): PlaylistTypeSpecificProperties {
            return objectMapper.convertValue(map, PlaylistTypeSpecificProperties::class.java)
        }
    }

    fun toMap(): Map<String, Any?> {
        return objectMapper.convertValue(this, object : TypeReference<Map<String, Any?>>() {})
    }

    class TrackInfo(
        val id: String,
        val addedAt: OffsetDateTime = OffsetDateTime.now(),
    )
}
