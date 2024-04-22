package edu.aua.spotifyserver.artists.persistence.mongodb.model

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

@Document(collection = "artists")
class ArtistDocument(
    @field:MongoId
    val id: String,
    val name: String,
    val displayImageUrl: String,
    val verified: Boolean,
    val aboutInfo: AboutInfoDocument,
) {

    class AboutInfoDocument(
        val description: String?,
        val imageUrls: Set<String> = emptySet(),
    )
}
