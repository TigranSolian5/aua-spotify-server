package edu.aua.spotifyserver.common.exceptions

class ValidationException : DomainException {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}
