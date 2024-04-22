package edu.aua.spotifyserver.collections.domain.model.specific

class PodcastTypeSpecificProperties(
    var description: String,
    var episodes: List<Episode>,
) {

    class Episode(
        var trackId: String,
        var description: String?,
    )
}
