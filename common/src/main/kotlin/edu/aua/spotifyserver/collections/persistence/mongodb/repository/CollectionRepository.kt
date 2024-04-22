package edu.aua.spotifyserver.collections.persistence.mongodb.repository

import edu.aua.spotifyserver.collections.persistence.mongodb.model.CollectionDocument
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CollectionRepository : ReactiveCrudRepository<CollectionDocument, String>
