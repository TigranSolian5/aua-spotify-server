package edu.aua.spotifyserver.configs.auth

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan

@ConfigurationProperties(prefix = "application.auth.jwt.secret-key")
@ConfigurationPropertiesScan
data class SecretKeyInfo(
    val id: String,
    val value: String,
)
