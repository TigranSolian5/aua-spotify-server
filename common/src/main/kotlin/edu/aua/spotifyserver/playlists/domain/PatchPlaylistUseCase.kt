package edu.aua.spotifyserver.playlists.domain

import edu.aua.spotifyserver.collections.domain.model.Collection
import edu.aua.spotifyserver.collections.domain.model.specific.PlaylistTypeSpecificProperties
import edu.aua.spotifyserver.collections.domain.usecase.GetCollectionsByIdsUseCase
import edu.aua.spotifyserver.collections.persistence.CollectionsPersistenceAdapter
import edu.aua.spotifyserver.common.exceptions.ConflictException
import edu.aua.spotifyserver.common.exceptions.ForbiddenException
import edu.aua.spotifyserver.common.exceptions.NotFoundException
import edu.aua.spotifyserver.common.exceptions.ValidationException
import edu.aua.spotifyserver.playlists.domain.model.PatchPlaylistProps
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class PatchPlaylistUseCase(
    private val getCollectionsByIdsUseCase: GetCollectionsByIdsUseCase,
    private val adapter: CollectionsPersistenceAdapter,
) {

    suspend fun patch(id: String, userId: String, props: PatchPlaylistProps) {
        prepareValidatedProps(props)

        val playlist = getCollectionsByIdsUseCase.getByIds(listOf(id))
            .firstOrNull { it.type == Collection.Type.playlist } ?: throw NotFoundException("Playlist $id does not exist")
        if (playlist.ownerId != userId) {
            throw ForbiddenException("User $userId does not own playlist $id")
        }

        val now = OffsetDateTime.now()

        props.caption?.let { playlist.caption = it }
        props.subCaption?.let { playlist.subCaption = it }
        props.displayImageUrl?.let { playlist.displayImageUrl = it }
        playlist.genres = playlist.genres + props.addGenres - props.deleteGenres

        val playlistSpecificProps = try {
            PlaylistTypeSpecificProperties.fromMap(playlist.typeSpecificProperties)
        } catch (e: IllegalArgumentException) {
            throw ConflictException("Playlist $id is stored in an unknown format")
        }
        props.public?.let { playlistSpecificProps.public = it }
        if (props.tracks.isNotEmpty()) {
            playlistSpecificProps.tracks = props.tracks.map {
                PlaylistTypeSpecificProperties.TrackInfo(it.id, addedAt = it.addedAt ?: now)
            }
        }
        if (props.addTracks.isNotEmpty()) {
            playlistSpecificProps.tracks += props.addTracks.toTrackInfos(now)
        }
        if (props.deleteTracks.isNotEmpty()) {
            playlistSpecificProps.tracks = playlistSpecificProps.tracks.filterNot { props.deleteTracks.contains(it.id) }
        }

        playlist.typeSpecificProperties = playlistSpecificProps.toMap()
        playlist.updatedAt = now
        adapter.save(playlist)
    }

    private fun prepareValidatedProps(props: PatchPlaylistProps) {
        with(props) {
            if (tracks.isNotEmpty() && (addTracks.isNotEmpty() || deleteTracks.isNotEmpty())) {
                throw ValidationException("Either tracks list OR add/delete sets must be provided, not both")
            }
        }
    }

    private fun List<String>.toTrackInfos(now: OffsetDateTime): List<PlaylistTypeSpecificProperties.TrackInfo> {
        return this.map { PlaylistTypeSpecificProperties.TrackInfo(it, addedAt = now) }
    }
}
