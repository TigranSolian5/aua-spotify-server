package edu.aua.spotifyserver.configs.auth.jwt

import edu.aua.spotifyserver.auth.jwt.JwtTokenService
import io.github.nefilim.kjwt.JWTClaims
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toMono

class JwtAuthenticationManager(
    private val jwtTokenService: JwtTokenService,
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        println("Auth Manager: $authentication")

        val tokenString = authentication.credentials as String

        return jwtTokenService.validateAndReturnClaims(tokenString)
            .toMono()
            .switchIfEmpty {
                Mono.defer { Mono.error(BadCredentialsException("Invalid JWT token provided")) }
            }
            .map {
                UsernamePasswordAuthenticationToken.authenticated(it.extractSubject(), tokenString, emptyList())
            }
    }

    private fun JWTClaims.extractSubject(): String {
        return this.subject().orNull() ?: throw BadCredentialsException("Invalid JWT token provided")
    }
}