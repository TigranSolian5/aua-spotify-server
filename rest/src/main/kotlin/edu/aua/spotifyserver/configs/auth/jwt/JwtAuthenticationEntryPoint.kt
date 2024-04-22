package edu.aua.spotifyserver.configs.auth.jwt

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class JwtAuthenticationEntryPoint : ServerAuthenticationEntryPoint {

    override fun commence(exchange: ServerWebExchange, ex: AuthenticationException): Mono<Void> {
        return Mono.fromRunnable {
            val response = exchange.response
            response.setStatusCode(HttpStatus.UNAUTHORIZED)
            response.headers[HttpHeaders.WWW_AUTHENTICATE] = "Bearer realm=\"/\""
        }
    }
}
