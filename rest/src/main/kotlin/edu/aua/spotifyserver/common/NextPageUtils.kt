package edu.aua.spotifyserver.common

import org.springframework.web.util.UriComponentsBuilder
import java.util.*

fun nextPageWithParams(
    uri: String,
    query: Map<String, Any?>,
): String {
    val builder = UriComponentsBuilder.fromHttpUrl(uri)
    query.entries.forEach {
        builder.queryParamIfPresent(it.key, Optional.ofNullable(it.value))
    }
    return builder.build().toUriString()
}
