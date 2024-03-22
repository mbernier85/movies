package im.bernier.movies.feature.authentication.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val success: Boolean,
    val expires_at: String,
    val request_token: String
)