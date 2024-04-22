package edu.aua.spotifyserver.users.domain

import edu.aua.spotifyserver.users.domain.model.CreateUserProps
import edu.aua.spotifyserver.users.domain.model.User
import edu.aua.spotifyserver.users.persistence.UsersPersistenceAdapter
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class CreateUserUseCase(
    private val adapter: UsersPersistenceAdapter,
) {

    suspend fun create(props: CreateUserProps): User {
        val user = with(props) {
            User(
                id = id,
                username = username,
                profileImageUrl = profileImageUrl,
                counters = User.Counters(),
                createdAt = OffsetDateTime.now(),
            )
        }

        return adapter.save(user)
    }
}
