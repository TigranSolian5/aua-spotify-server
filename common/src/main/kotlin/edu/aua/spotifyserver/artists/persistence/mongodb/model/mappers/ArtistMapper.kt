package edu.aua.spotifyserver.artists.persistence.mongodb.model.mappers

import edu.aua.spotifyserver.artists.domain.model.Artist
import edu.aua.spotifyserver.artists.persistence.mongodb.model.ArtistDocument
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface ArtistMapper {
    companion object {
        val INSTANCE: ArtistMapper = Mappers.getMapper(ArtistMapper::class.java)
    }

    fun domainObjectToEntity(artist: Artist): ArtistDocument
    fun entityToDomainObject(artist: ArtistDocument): Artist
}
