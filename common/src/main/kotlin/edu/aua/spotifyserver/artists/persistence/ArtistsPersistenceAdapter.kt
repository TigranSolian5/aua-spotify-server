package edu.aua.spotifyserver.artists.persistence

import edu.aua.spotifyserver.artists.domain.model.Artist
import edu.aua.spotifyserver.artists.persistence.mongodb.model.mappers.ArtistMapper
import edu.aua.spotifyserver.artists.persistence.mongodb.repository.ArtistRepository
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component

@Component
class ArtistsPersistenceAdapter(
    private val repository: ArtistRepository,
) {

    suspend fun save(artist: Artist): Artist {
        val entity = ArtistMapper.INSTANCE.domainObjectToEntity(artist)
        val savedDocument = repository.save(entity).awaitSingle()
        return ArtistMapper.INSTANCE.entityToDomainObject(savedDocument)
    }
}
