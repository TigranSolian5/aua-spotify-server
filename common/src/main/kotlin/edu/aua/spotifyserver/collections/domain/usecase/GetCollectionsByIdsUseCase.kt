package edu.aua.spotifyserver.collections.domain.usecase

import edu.aua.spotifyserver.collections.domain.model.Collection
import edu.aua.spotifyserver.collections.persistence.CollectionsPersistenceAdapter
import org.springframework.stereotype.Component

@Component
class GetCollectionsByIdsUseCase(
    private val adapter: CollectionsPersistenceAdapter,
) {

    suspend fun getByIds(ids: List<String>): List<Collection> {
        return adapter.getByIds(ids)
    }
}
