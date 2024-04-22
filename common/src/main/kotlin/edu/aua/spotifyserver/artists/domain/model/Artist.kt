package edu.aua.spotifyserver.artists.domain.model

class Artist(
    val id: String,
    var name: String,
    var displayImageUrl: String,
    var verified: Boolean,
    val aboutInfo: AboutInfo,
) {

    class AboutInfo(
        var description: String?,
        var imageUrls: Set<String> = emptySet(),
    )
}
