package im.bernier.movies.feature.watchlist

import androidx.lifecycle.LiveData
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
    fun getMovieWatchList(): LiveData<List<MovieWatchListItem>>

    @Query("SELECT * FROM ShowWatchListItem")
    fun getShowWatchList(): LiveData<List<ShowWatchListItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieWatchList(movieWatchList: List<MovieWatchListItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShowWatchList(showWatchList: List<ShowWatchListItem>)
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
