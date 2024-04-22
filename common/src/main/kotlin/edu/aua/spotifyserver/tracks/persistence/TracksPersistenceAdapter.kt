package edu.aua.spotifyserver.tracks.persistence

import edu.aua.spotifyserver.tracks.domain.model.Track
import edu.aua.spotifyserver.tracks.persistence.mongodb.model.mappers.TrackMapper
import edu.aua.spotifyserver.tracks.persistence.mongodb.repository.TrackRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component

@Component
class TracksPersistenceAdapter(
    private val repository: TrackRepository,
) {

    suspend fun getByIds(ids: List<String>): List<Track> {
        return repository.findAllById(ids)
            .map { TrackMapper.INSTANCE.entityToDomainObject(it) }
            .asFlow()
            .toList()
    }

    suspend fun saveTrack(track: Track): Track {
        val entity = TrackMapper.INSTANCE.domainObjectToEntity(track)
        val savedDocument = repository.save(entity).awaitSingle()
        return TrackMapper.INSTANCE.entityToDomainObject(savedDocument)
    }
}
