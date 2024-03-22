package im.bernier.movies.feature.authentication.model

import kotlinx.serialization.Serializable

@Serializable
data class ValidateTokenResponse(
    val success: Boolean,
    val request_token: String,
    val expires_at: String
)
