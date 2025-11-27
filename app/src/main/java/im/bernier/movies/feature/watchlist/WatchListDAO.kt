package im.bernier.movies.feature.watchlist

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.serialization.Serializable

@Dao
interface WatchListDAO {
    @Query("SELECT * FROM MovieWatchListItem")
    suspend fun getMovieWatchList(): List<MovieWatchListItem>

    @Query("SELECT * FROM ShowWatchListItem")
    suspend fun getShowWatchList(): List<ShowWatchListItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieWatchList(movieWatchList: List<MovieWatchListItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShowWatchList(showWatchList: List<ShowWatchListItem>)
}

@Entity
@Serializable
data class MovieWatchListItem(
    @PrimaryKey(autoGenerate = false) val id: Long,
)

@Entity
@Serializable
data class ShowWatchListItem(
    @PrimaryKey(autoGenerate = false) val id: Long,
)
