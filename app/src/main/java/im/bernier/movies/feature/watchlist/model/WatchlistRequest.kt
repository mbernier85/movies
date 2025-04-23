package im.bernier.movies.feature.watchlist.model

import kotlinx.serialization.Serializable

@Serializable
data class WatchlistRequest(
    val media_type: String,
    val media_id: Long,
    val watchlist: Boolean,
)
