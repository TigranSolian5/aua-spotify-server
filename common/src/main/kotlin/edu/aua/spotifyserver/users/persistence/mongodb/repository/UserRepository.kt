package edu.aua.spotifyserver.users.persistence.mongodb.repository

import edu.aua.spotifyserver.users.persistence.mongodb.model.UserDocument
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : ReactiveCrudRepository<UserDocument, String>
