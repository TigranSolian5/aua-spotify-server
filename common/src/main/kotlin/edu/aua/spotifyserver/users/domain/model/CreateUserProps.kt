package edu.aua.spotifyserver.users.domain.model

data class CreateUserProps(
    val id: String,
    val username: String,
    val profileImageUrl: String,
)
