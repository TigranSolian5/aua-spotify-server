package edu.aua.spotifyserver.users.domain

import edu.aua.spotifyserver.users.domain.model.User
import edu.aua.spotifyserver.users.persistence.UsersPersistenceAdapter
import org.springframework.stereotype.Component

@Component
class GetUserByIdUseCase(
    private val adapter: UsersPersistenceAdapter,
) {

    suspend fun getById(id: String): User? {
        return adapter.getById(id)
    }
}