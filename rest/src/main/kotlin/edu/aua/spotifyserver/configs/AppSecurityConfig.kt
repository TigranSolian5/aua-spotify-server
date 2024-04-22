package edu.aua.spotifyserver.configs

import edu.aua.spotifyserver.auth.jwt.JwtTokenService
import edu.aua.spotifyserver.configs.auth.jwt.JwtAuthenticationConverter
import edu.aua.spotifyserver.configs.auth.jwt.JwtAuthenticationEntryPoint
import edu.aua.spotifyserver.configs.auth.jwt.JwtAuthenticationManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler


@Configuration
@EnableWebFluxSecurity
class AppSecurityConfig {

    @Bean
    @Primary
    fun jwtAuthenticationManager(
        jwtTokenService: JwtTokenService
    ): ReactiveAuthenticationManager = JwtAuthenticationManager(jwtTokenService)

    @Bean
    fun jwtAuthenticationConverter(): ServerAuthenticationConverter = JwtAuthenticationConverter()

    @Bean
    fun jwtAuthenticationEntryPoint(): ServerAuthenticationEntryPoint = JwtAuthenticationEntryPoint()

    @Bean
    fun jwtAuthenticationFailureHandler(
        jwtAuthenticationEntryPoint: ServerAuthenticationEntryPoint,
    ): ServerAuthenticationFailureHandler {
        val handler = ServerAuthenticationEntryPointFailureHandler(jwtAuthenticationEntryPoint)
        handler.setRethrowAuthenticationServiceException(false)
        return handler
    }

    @Bean
    fun jwtTokenAuthenticationFilter(
        authenticationManager: ReactiveAuthenticationManager,
        authenticationConverter: ServerAuthenticationConverter,
        authenticationFailureHandler: ServerAuthenticationFailureHandler,
    ): AuthenticationWebFilter {
        val filter = AuthenticationWebFilter(authenticationManager)
        filter.setServerAuthenticationConverter(authenticationConverter)
        filter.setAuthenticationFailureHandler(authenticationFailureHandler)
        return filter
    }

    @Bean
    fun springSecurityFilterChain(
        http: ServerHttpSecurity,
        jwtTokenAuthenticationFilter: AuthenticationWebFilter,
    ): SecurityWebFilterChain {
        return http
            .csrf { it.disable() }
            .cors { it.disable() }
            .authorizeExchange { exchange ->
                exchange
//                    .pathMatchers("/v1/auth/**", "/v1/playlists/*/display").permitAll()
                    .pathMatchers("/v1/auth/sign-in").permitAll()
                    .anyExchange().authenticated()
//                    .pathMatchers(HttpMethod.PATCH, "/v1/playlists/*").authenticated()
//                    .pathMatchers(HttpMethod.POST, "/v1/playlists").authenticated()
            }
            .addFilterAt(jwtTokenAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .build()
    }
}
