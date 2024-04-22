package edu.aua.spotifyserver.configs.auth

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.apache.v2.ApacheHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GoogleOAuthConfig {

    @Bean
    fun googleIdTokenVerifier(
        httpTransport: HttpTransport,
        jsonFactory: JsonFactory,
        @Value("\${application.auth.google.client-id}") clientId: String,
    ): GoogleIdTokenVerifier {
        return GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory)
            .setAudience(listOf(clientId))
            .build()
    }

    @Bean
    fun defaultHttpTransport(): HttpTransport {
        return ApacheHttpTransport()
    }

    @Bean
    fun defaultJsonFactory(): JsonFactory {
        return GsonFactory.getDefaultInstance()
    }
}
