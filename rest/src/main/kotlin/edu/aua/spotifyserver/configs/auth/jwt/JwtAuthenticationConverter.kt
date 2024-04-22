package edu.aua.spotifyserver.configs.auth.jwt

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.util.StringUtils
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

private const val BEARER = "Bearer "

class JwtAuthenticationConverter : ServerAuthenticationConverter {

    override fun convert(exchange: ServerWebExchange): Mono<Authentication> {
        val request = exchange.request
        val authToken = request.headers.getFirst(HttpHeaders.AUTHORIZATION)

        println("Auth Converter: $authToken")

        return authToken.toMono()
            .filter { StringUtils.hasText(it) && it.startsWith(BEARER) }
            .map { it.substring(BEARER.length) }
            .map {
                UsernamePasswordAuthenticationToken.unauthenticated(it, it)
            }
    }
}