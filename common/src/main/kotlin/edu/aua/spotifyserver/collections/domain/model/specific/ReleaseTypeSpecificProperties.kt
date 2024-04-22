package edu.aua.spotifyserver.collections.domain.model.specific

class ReleaseTypeSpecificProperties(
    var type: Type,
    var labelInfo: Any, //TODO introduce type for label info
    var disks: List<Disk>,
) {

    class Disk(
        var name: String,
        var trackIds: List<String>,
    )

    enum class Type {
        single,
        ep,
        album,
        compilation;
    }
}
