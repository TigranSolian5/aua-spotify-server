package edu.aua.spotifyserver.users.persistence.mongodb.model

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user_followers")
class UserFollowersDocument(
    @field:Indexed(unique = true)
    val userId: String,
    @field:Indexed
    val followersIds: Set<String>,
)
