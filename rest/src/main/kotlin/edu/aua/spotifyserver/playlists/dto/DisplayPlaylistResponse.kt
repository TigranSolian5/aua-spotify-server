package edu.aua.spotifyserver.playlists.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.OffsetDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
class DisplayPlaylistResponse(
    val id: String,
    val public: Boolean,
    val owner: OwnerDto,
    val caption: String,
    val subCaption: String?,
    val displayImageUrl: String,
    val trackCount: Int,
    val duration: Long,
    val genres: Set<String>,
    val likeCount: Int,
    val tracks: List<PlaylistTrackDto>,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime,
) {

    class OwnerDto(
        val id: String,
        val name: String,
    )
}
