package edu.aua.spotifyserver.tracks.domain.usecase

import edu.aua.spotifyserver.tracks.domain.model.Track
import edu.aua.spotifyserver.tracks.persistence.TracksPersistenceAdapter
import org.springframework.stereotype.Component

@Component
class GetTracksByIdsUseCase(
    private val adapter: TracksPersistenceAdapter,
) {

    suspend fun getByIds(ids: List<String>, keepOrder: Boolean = false): List<Track> {
        var items = adapter.getByIds(ids)

        if (keepOrder) {
            val indexByItemIds = items.withIndex().associate { it.value.id to it.index }
            items = items.sortedBy { indexByItemIds[it.id] }
        }

        return items
    }
}