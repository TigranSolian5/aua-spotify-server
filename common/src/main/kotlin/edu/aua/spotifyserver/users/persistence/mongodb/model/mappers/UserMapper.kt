package edu.aua.spotifyserver.users.persistence.mongodb.model.mappers

import edu.aua.spotifyserver.users.domain.model.User
import edu.aua.spotifyserver.users.persistence.mongodb.model.UserDocument
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface UserMapper {
    companion object {
        val INSTANCE: UserMapper = Mappers.getMapper(UserMapper::class.java)
    }

    fun entityToDomainModel(user: UserDocument): User
    fun entityToDomainModel(counters: UserDocument.CountersDocument): User.Counters
    fun entityToDomainModel(
        playlistCounters: UserDocument.CountersDocument.PlaylistCountersDocument
    ): User.Counters.PlaylistCounters
    fun entityToDomainModel(
        followsCounters: UserDocument.CountersDocument.FollowsCountersDocument
    ): User.Counters.FollowsCounters

    fun domainModelToEntity(user: User): UserDocument
    fun domainModelToEntity(counters: User.Counters): UserDocument.CountersDocument
    fun domainModelToEntity(
        playlistCounters: User.Counters.PlaylistCounters
    ): UserDocument.CountersDocument.PlaylistCountersDocument
    fun domainModelToEntity(
        followsCounters: User.Counters.FollowsCounters
    ): UserDocument.CountersDocument.FollowsCountersDocument

}
