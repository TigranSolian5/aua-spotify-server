package edu.aua.spotifyserver.tracks.domain.usecase

import edu.aua.spotifyserver.common.exceptions.ValidationException
import edu.aua.spotifyserver.common.mongodb.generateObjectId
import edu.aua.spotifyserver.tracks.domain.model.Track
import edu.aua.spotifyserver.tracks.domain.model.props.CreateTrackProps
import edu.aua.spotifyserver.tracks.persistence.TracksPersistenceAdapter
import org.springframework.stereotype.Component

@Component
class CreateTrackUseCase(
    private val adapter: TracksPersistenceAdapter,
) {

    suspend fun create(props: CreateTrackProps): Track {
        validateProps(props)

        val track = Track(
            id = generateObjectId(),
            artistIds = props.artistIds,
            collectionId = props.collectionId,
            trackUrl = props.trackUrl,
            duration = props.duration,
            caption = props.caption,
            genres = props.genres,
        )
        return adapter.saveTrack(track)

        // TODO publish event
    }

    private fun validateProps(props: CreateTrackProps) {
        with(props) {
            if (artistIds.isEmpty()) {
                throw ValidationException("Track cannot be created: artistIds must not be empty")
            }

            // TODO check all genre keys exist and populate parent keys as well.
        }
    }
}