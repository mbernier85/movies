package im.bernier.movies.feature.authentication.model

import kotlinx.serialization.Serializable

@Serializable
data class SessionResponse(val success: Boolean, val session_id: String)
