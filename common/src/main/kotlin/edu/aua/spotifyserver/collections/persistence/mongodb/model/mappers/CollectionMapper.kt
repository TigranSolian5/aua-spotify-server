package edu.aua.spotifyserver.collections.persistence.mongodb.model.mappers

import edu.aua.spotifyserver.collections.domain.model.Collection
import edu.aua.spotifyserver.collections.persistence.mongodb.model.CollectionDocument
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface CollectionMapper {
    companion object { val INSTANCE: CollectionMapper = Mappers.getMapper(CollectionMapper::class.java) }

    fun domainObjectToEntity(collection: Collection): CollectionDocument
    fun entityToDomainObject(collection: CollectionDocument): Collection
}
