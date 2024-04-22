package edu.aua.spotifyserver.tracks.persistence.mongodb.repository

import edu.aua.spotifyserver.tracks.persistence.mongodb.model.TrackDocument
import org.bson.types.ObjectId
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TrackRepository : ReactiveCrudRepository<TrackDocument, String>
