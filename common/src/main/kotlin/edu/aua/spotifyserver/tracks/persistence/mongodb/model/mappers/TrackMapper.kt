package edu.aua.spotifyserver.tracks.persistence.mongodb.model.mappers

import edu.aua.spotifyserver.tracks.domain.model.Track
import edu.aua.spotifyserver.tracks.persistence.mongodb.model.TrackDocument
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface TrackMapper {
    companion object { val INSTANCE: TrackMapper = Mappers.getMapper(TrackMapper::class.java) }

    fun domainObjectToEntity(track: Track): TrackDocument
    fun entityToDomainObject(track: TrackDocument): Track
}
