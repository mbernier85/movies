package im.bernier.movies.feature.authentication.model

import kotlinx.serialization.Serializable

@Serializable
data class ValidateTokenRequest(
    val username: String,
    val password: String,
    val request_token: String,
)
