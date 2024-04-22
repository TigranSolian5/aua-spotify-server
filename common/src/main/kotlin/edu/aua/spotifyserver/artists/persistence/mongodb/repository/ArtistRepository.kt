package edu.aua.spotifyserver.artists.persistence.mongodb.repository

import edu.aua.spotifyserver.artists.persistence.mongodb.model.ArtistDocument
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ArtistRepository : ReactiveCrudRepository<ArtistDocument, String>
