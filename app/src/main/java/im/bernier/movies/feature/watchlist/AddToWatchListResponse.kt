package im.bernier.movies.feature.watchlist

import kotlinx.serialization.Serializable

@Serializable
data class AddToWatchListResponse(
    val success: Boolean,
    val status_message: String? = null,
    val status_code: Int? = null,
)
