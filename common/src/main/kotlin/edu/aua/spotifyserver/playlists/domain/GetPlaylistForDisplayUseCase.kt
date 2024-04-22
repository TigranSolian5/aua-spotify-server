package edu.aua.spotifyserver.playlists.domain

import edu.aua.spotifyserver.artists.domain.model.Artist
import edu.aua.spotifyserver.artists.domain.usecase.GetArtistsByIdsUseCase
import edu.aua.spotifyserver.collections.domain.model.Collection
import edu.aua.spotifyserver.collections.domain.model.specific.PlaylistTypeSpecificProperties
import edu.aua.spotifyserver.collections.domain.usecase.GetCollectionsByIdsUseCase
import edu.aua.spotifyserver.common.exceptions.NotFoundException
import edu.aua.spotifyserver.playlists.domain.model.Playlist
import edu.aua.spotifyserver.playlists.domain.model.PlaylistTrack
import edu.aua.spotifyserver.playlists.domain.model.mappers.PlaylistComponentMapper
import edu.aua.spotifyserver.playlists.persistence.PlaylistPersistenceAdapter
import edu.aua.spotifyserver.tracks.domain.model.Track
import edu.aua.spotifyserver.tracks.domain.usecase.GetTracksByIdsUseCase
import edu.aua.spotifyserver.users.domain.GetUserByIdUseCase
import org.springframework.stereotype.Component

@Component
class GetPlaylistForDisplayUseCase(
    private val adapter: PlaylistPersistenceAdapter,
    private val getTracksByIdsUseCase: GetTracksByIdsUseCase,
    private val getArtistsByIdsUseCase: GetArtistsByIdsUseCase,
    private val getCollectionsByIdsUseCase: GetCollectionsByIdsUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
) {

    suspend fun getById(id: String, trackOffset: Int? = null, trackLimit: Int? = null): Playlist {
        val playlist = adapter.getById(id, trackOffset, trackLimit)
            ?: throw NotFoundException("Playlist $id does not exist")
        val props = PlaylistTypeSpecificProperties.fromMap(playlist.typeSpecificProperties)
        val playlistTracks = preparePlaylistTracks(props)

        val user = getUserByIdUseCase.getById(playlist.ownerId)!!
        val playlistOwner = PlaylistComponentMapper.INSTANCE.userToPlaylistOwner(user)

        return Playlist(
            id = playlist.id,
            public = props.public,
            owner = playlistOwner,
            caption = playlist.caption,
            subCaption = playlist.subCaption,
            displayImageUrl = playlist.displayImageUrl,
            trackCount = playlist.trackCount,
            duration = playlist.duration,
            genres = playlist.genres,
            likeCount = props.likeCount,
            tracks = playlistTracks,
            createdAt = playlist.createdAt,
            updatedAt = playlist.updatedAt,
        )
    }

    suspend fun getPlaylistTracks(id: String, trackOffset: Int? = null, trackLimit: Int? = null): List<PlaylistTrack> {
        val playlist = adapter.getById(id, trackOffset, trackLimit)
            ?: throw NotFoundException("Playlist $id does not exist")
        val props = PlaylistTypeSpecificProperties.fromMap(playlist.typeSpecificProperties)

        return preparePlaylistTracks(props)
    }

    private suspend fun preparePlaylistTracks(props: PlaylistTypeSpecificProperties): List<PlaylistTrack> {
        val tracks = getTracksByIdsUseCase.getByIds(
            ids = props.tracks.map { it.id },
            keepOrder = true,
        )
        val tracksByIds = tracks.associateBy { it.id }
        val artistsByIds = tracks.retrieveArtistsByIdsMapping()
        val albumsByIds = tracks.retrieveAlbumsByIdsMapping()

        return props.tracks.map {
            val track = tracksByIds[it.id]!!
            val addedAt = it.addedAt
            val trackArtists = track.artistIds
                .mapNotNull { artistId -> artistsByIds[artistId] }
                .map { artist -> PlaylistComponentMapper.INSTANCE.artistToPlaylistArtist(artist) }
            val album = albumsByIds[track.collectionId]!!.let { collection ->
                PlaylistComponentMapper.INSTANCE.collectionToPlaylistAlbum(collection)
            }
            val playbackInfo = PlaylistComponentMapper.INSTANCE.trackToPlaybackInfo(track)

            PlaylistTrack(
                id = it.id,
                caption = track.caption,
                artists = trackArtists,
                album = album,
                playback = playbackInfo,
                addedAt = addedAt,
            )
        }
    }

    private suspend fun List<Track>.retrieveArtistsByIdsMapping(): Map<String, Artist> {
        val artists = getArtistsByIdsUseCase.getByIds(this.flatMap { it.artistIds })
        return artists.associateBy { it.id }
    }

    private suspend fun List<Track>.retrieveAlbumsByIdsMapping(): Map<String, Collection> {
        val albums = getCollectionsByIdsUseCase.getByIds(this.map { it.collectionId })
        return albums.associateBy { it.id }
    }
}
