package edu.aua.spotifyserver.collections.domain.usecase.validator

import edu.aua.spotifyserver.collections.domain.model.props.CreateCollectionProps
import edu.aua.spotifyserver.collections.domain.model.specific.PlaylistTypeSpecificProperties
import edu.aua.spotifyserver.common.exceptions.ValidationException
import edu.aua.spotifyserver.tracks.domain.usecase.GetTracksByIdsUseCase
import org.springframework.stereotype.Component

@Component
class PlaylistPropertiesValidator(
    private val getTracksByIdsUseCase: GetTracksByIdsUseCase,
) : CollectionTypeSpecificPropertiesValidator {

    override suspend fun prepareValidatedProps(props: CreateCollectionProps) {
        val playlistProps = PlaylistTypeSpecificProperties.fromMap(props.typeSpecificProperties)
        allTracksInPlaylistMustExist(playlistProps)
        props.typeSpecificProperties = playlistProps.toMap()

        props.trackCount = playlistProps.tracks.size
    }

    private suspend fun allTracksInPlaylistMustExist(props: PlaylistTypeSpecificProperties) {
        val trackIds = props.tracks.map { it.id }
        val actualTrackIds = getTracksByIdsUseCase.getByIds(trackIds).map { it.id }
        if (trackIds.size != actualTrackIds.size) {
            throw ValidationException("Playlist validation exception: some of the tracks in playlist do not exist")
        }
    }
}
