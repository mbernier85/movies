package im.bernier.movies.feature.authentication.model

import kotlinx.serialization.Serializable

@Serializable
data class SessionRequest(val request_token: String)
