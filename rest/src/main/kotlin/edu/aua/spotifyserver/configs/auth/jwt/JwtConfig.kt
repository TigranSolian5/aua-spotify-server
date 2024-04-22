package edu.aua.spotifyserver.configs.auth.jwt

import io.github.nefilim.kjwt.ClaimsValidator
import io.github.nefilim.kjwt.ClaimsVerification
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JwtConfig {

    @Bean
    fun defaultClaimsValidator(
        @Value("\${spring.application.name}")
        applicationName: String,
    ): ClaimsValidator {
        return { claims ->
            ClaimsVerification.validateClaims(
                ClaimsVerification.issuer(applicationName),
            )(claims)
        }
    }
}
