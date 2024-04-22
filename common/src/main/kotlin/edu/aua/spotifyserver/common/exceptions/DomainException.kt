package edu.aua.spotifyserver.common.exceptions

sealed class DomainException : RuntimeException {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}
