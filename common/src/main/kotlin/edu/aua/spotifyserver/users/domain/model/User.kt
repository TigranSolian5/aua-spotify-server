package edu.aua.spotifyserver.users.domain.model

import java.time.OffsetDateTime

data class User(
    val id: String,
    val username: String,
    val profileImageUrl: String,
    val counters: Counters,
    val createdAt: OffsetDateTime,
) {

    class Counters(
        val playlists: PlaylistCounters = PlaylistCounters(),
        val follows: FollowsCounters = FollowsCounters(),
    ) {

        class PlaylistCounters(
            val public: Int = 0,
            val private: Int = 0,
        )

        class FollowsCounters(
            val follower: Int = 0,
            val following: Int = 0,
        )
    }
}
