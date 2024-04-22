package edu.aua.spotifyserver.collections.persistence

import edu.aua.spotifyserver.collections.domain.model.Collection
import edu.aua.spotifyserver.collections.persistence.mongodb.model.mappers.CollectionMapper
import edu.aua.spotifyserver.collections.persistence.mongodb.repository.CollectionRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component

@Component
class CollectionsPersistenceAdapter(
    private val repository: CollectionRepository,
) {

    suspend fun getByIds(ids: List<String>): List<Collection> {
        return repository.findAllById(ids)
            .map { CollectionMapper.INSTANCE.entityToDomainObject(it) }
            .asFlow()
            .toList()
    }

    suspend fun saveAll(collections: Set<Collection>) {
        if (collections.isEmpty()) return

        collections
            .map { CollectionMapper.INSTANCE.domainObjectToEntity(it) }
            .let { repository.saveAll(it) }
    }

    suspend fun save(collection: Collection): Collection {
        val entity = CollectionMapper.INSTANCE.domainObjectToEntity(collection)
        val savedDocument = repository.save(entity).awaitSingle()
        return CollectionMapper.INSTANCE.entityToDomainObject(savedDocument)
    }
}
