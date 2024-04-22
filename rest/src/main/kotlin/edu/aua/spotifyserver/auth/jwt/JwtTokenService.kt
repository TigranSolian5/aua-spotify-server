package edu.aua.spotifyserver.auth.jwt

import arrow.core.getOrHandle
import arrow.core.orNull
import edu.aua.spotifyserver.configs.auth.SecretKeyInfo
import edu.aua.spotifyserver.users.domain.model.User
import io.github.nefilim.kjwt.ClaimsValidator
import io.github.nefilim.kjwt.JWSHMAC256Algorithm
import io.github.nefilim.kjwt.JWT
import io.github.nefilim.kjwt.JWTClaims
import io.github.nefilim.kjwt.SignedJWT
import io.github.nefilim.kjwt.sign
import io.github.nefilim.kjwt.toJWTKeyID
import io.github.nefilim.kjwt.verify
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class JwtTokenService(
    private val secretKeyInfo: SecretKeyInfo,
    @Value("\${spring.application.name}")
    private val applicationName: String,
    private val claimsValidator: ClaimsValidator,
) {

    fun createFor(user: User): SignedJWT<JWSHMAC256Algorithm> {
        val keyId = secretKeyInfo.id.toJWTKeyID()
        val jwt = JWT.hs256(keyId) {
            subject(user.id)
            issuer(applicationName)
            issuedAt(Instant.now())
        }

        val signResult = jwt.sign(secretKeyInfo.value)
        return signResult.getOrHandle { throw InternalAuthenticationServiceException("Exception when signing JWT token") }
    }

    fun validateAndReturnClaims(token: String): JWTClaims? {
        val result = verify<JWSHMAC256Algorithm>(token, secretKeyInfo.value, claimsValidator)
        return result.orNull()
    }
}
