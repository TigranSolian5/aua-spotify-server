package edu.aua.spotifyserver.auth

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import edu.aua.spotifyserver.auth.dto.UserSignInRequest
import edu.aua.spotifyserver.auth.dto.UserSignInResponse
import edu.aua.spotifyserver.auth.jwt.JwtTokenService
import edu.aua.spotifyserver.users.domain.CreateUserUseCase
import edu.aua.spotifyserver.users.domain.GetUserByIdUseCase
import edu.aua.spotifyserver.users.domain.model.CreateUserProps
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/auth")
class UserSignInController(
    private val tokenVerifier: GoogleIdTokenVerifier,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val jwtTokenService: JwtTokenService,
) {

    @PostMapping("/sign-in")
    suspend fun signIn(
        @RequestBody request: UserSignInRequest,
    ): UserSignInResponse {
        val tokenString = request.idToken
        val idToken = tokenVerifier.verify(tokenString) ?: throw BadCredentialsException("Invalid ID token provided")

        val payload = idToken.payload

        val userId = payload.subject
        val username = payload["name"] as String
        val profileImageUrl = payload["picture"] as String

        val authenticatedUser = getUserByIdUseCase.getById(userId)
            ?: createUserUseCase.create(CreateUserProps(userId, username, profileImageUrl))
        val jwt = jwtTokenService.createFor(authenticatedUser)

        return UserSignInResponse(
            authToken = jwt.rendered
        )
    }
}