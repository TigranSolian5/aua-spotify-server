package edu.aua.spotifyserver.artists.domain.usecase

import edu.aua.spotifyserver.artists.domain.model.Artist
import org.springframework.stereotype.Component

@Component
class GetArtistsByIdsUseCase {

    suspend fun getByIds(ids: List<String>): List<Artist> {
        // TODO implement this
        return listOf()
    }
}
