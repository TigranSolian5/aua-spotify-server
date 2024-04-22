package edu.aua.spotifyserver.playlists.domain.model.mappers

import edu.aua.spotifyserver.artists.domain.model.Artist
import edu.aua.spotifyserver.collections.domain.model.Collection
import edu.aua.spotifyserver.playlists.domain.model.Playlist
import edu.aua.spotifyserver.playlists.domain.model.PlaylistTrack
import edu.aua.spotifyserver.tracks.domain.model.Track
import edu.aua.spotifyserver.users.domain.model.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

@Mapper
interface PlaylistComponentMapper {
    companion object {
        val INSTANCE: PlaylistComponentMapper = Mappers.getMapper(PlaylistComponentMapper::class.java)
    }

    fun artistToPlaylistArtist(artist: Artist): PlaylistTrack.Artist
    fun collectionToPlaylistAlbum(collection: Collection): PlaylistTrack.Album

    @Mapping(source = "username", target = "name")
    fun userToPlaylistOwner(user: User): Playlist.Owner

    @Mapping(source = "trackUrl", target = "url")
    fun trackToPlaybackInfo(track: Track): PlaylistTrack.PlaybackInfo
}
