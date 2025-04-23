package im.bernier.movies.feature.watchlist

import androidx.room.Entity
import kotlinx.serialization.Serializable

interface WatchListDAO

@Entity
@Serializable
data class WatchList(
    val id: Long,
    val movie: Boolean,
)
