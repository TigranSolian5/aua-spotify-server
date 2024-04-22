package edu.aua.spotifyserver.common.exceptions

class NotFoundException : DomainException {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}
