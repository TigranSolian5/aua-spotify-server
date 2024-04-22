package edu.aua.spotifyserver.playlists.dto.mappers

import edu.aua.spotifyserver.playlists.domain.model.PatchPlaylistProps
import edu.aua.spotifyserver.playlists.domain.model.Playlist
import edu.aua.spotifyserver.playlists.domain.model.PlaylistTrack
import edu.aua.spotifyserver.playlists.dto.DisplayPlaylistResponse
import edu.aua.spotifyserver.playlists.dto.PatchPlaylistRequest
import edu.aua.spotifyserver.playlists.dto.PlaylistTrackDto
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface PlaylistMapper {
    companion object {
        val INSTANCE: PlaylistMapper = Mappers.getMapper(PlaylistMapper::class.java)
    }

    fun requestToProps(req: PatchPlaylistRequest): PatchPlaylistProps
    fun domainToResponse(playlist: Playlist): DisplayPlaylistResponse
    fun domainToResponse(owner: Playlist.Owner): DisplayPlaylistResponse.OwnerDto
    fun domainToResponse(track: PlaylistTrack): PlaylistTrackDto
    fun domainToResponse(playbackInfo: PlaylistTrack.PlaybackInfo): PlaylistTrackDto.PlaybackInfoDto
    fun domainToResponse(artist: PlaylistTrack.Artist): PlaylistTrackDto.ArtistDto
    fun domainToResponse(album: PlaylistTrack.Album): PlaylistTrackDto.AlbumDto
}
