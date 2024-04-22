package edu.aua.spotifyserver.playlists.persistence

import edu.aua.spotifyserver.collections.domain.model.Collection
import edu.aua.spotifyserver.collections.persistence.mongodb.model.CollectionDocument
import edu.aua.spotifyserver.collections.persistence.mongodb.model.mappers.CollectionMapper
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component

private const val JSON_PROPERTY_PLAYLIST_TRACKS = "typeSpecificProperties.tracks"

@Component
class PlaylistPersistenceAdapter(
    private val mongoTemplate: ReactiveMongoTemplate,
) {

    suspend fun getById(id: String, trackOffset: Int? = null, trackLimit: Int? = null): Collection? {
        val query = Query(
            Criteria.where("_id").`is`(id)
                .and("type").`is`(Collection.Type.playlist.name)
        )
        if (trackOffset != null) {
            if (trackLimit != null) {
                query.fields().slice(JSON_PROPERTY_PLAYLIST_TRACKS, trackOffset, trackLimit)
            } else {
                query.fields().slice(JSON_PROPERTY_PLAYLIST_TRACKS, trackOffset)
            }
        }

        return mongoTemplate.findOne(query, CollectionDocument::class.java, "collections")
            .map { CollectionMapper.INSTANCE.entityToDomainObject(it) }
            .awaitSingleOrNull()
    }
}
