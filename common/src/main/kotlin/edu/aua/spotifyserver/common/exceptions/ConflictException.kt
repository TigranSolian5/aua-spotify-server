package edu.aua.spotifyserver.common.exceptions

class ConflictException : DomainException {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}
