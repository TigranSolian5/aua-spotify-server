package edu.aua.spotifyserver.users.persistence.mongodb.model

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import java.time.OffsetDateTime

@Document(collection = "users")
class UserDocument(
    @field:MongoId
    val id: String,
    val username: String,
    val profileImageUrl: String,
    val counters: CountersDocument = CountersDocument(),
    val createdAt: OffsetDateTime,
) {

    class CountersDocument(
        val playlists: PlaylistCountersDocument = PlaylistCountersDocument(),
        val follows: FollowsCountersDocument = FollowsCountersDocument(),
    ) {

        class PlaylistCountersDocument(
            val public: Int = 0,
            val private: Int = 0,
        )

        class FollowsCountersDocument(
            val follower: Int = 0,
            val following: Int = 0,
        )
    }
}
