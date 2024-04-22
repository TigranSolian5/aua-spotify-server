package edu.aua.spotifyserver.users.persistence

import edu.aua.spotifyserver.users.domain.model.User
import edu.aua.spotifyserver.users.persistence.mongodb.model.mappers.UserMapper
import edu.aua.spotifyserver.users.persistence.mongodb.repository.UserRepository
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Component

@Component
class UsersPersistenceAdapter(
    private val repository: UserRepository,
) {

    suspend fun save(user: User): User {
        val entity = UserMapper.INSTANCE.domainModelToEntity(user)
        val savedDocument = repository.save(entity).awaitSingle()
        return UserMapper.INSTANCE.entityToDomainModel(savedDocument)
    }

    suspend fun getById(id: String): User? {
        return repository.findById(id)
            .map { UserMapper.INSTANCE.entityToDomainModel(it) }
            .awaitSingleOrNull()
    }
}