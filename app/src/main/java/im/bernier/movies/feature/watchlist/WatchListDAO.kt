package im.bernier.movies.feature.watchlist

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

@Dao
interface WatchListDAO {
    @Query("SELECT * FROM MovieWatchListItem")
    suspend fun getMovieWatchList(): List<MovieWatchListItem>

    @Query("SELECT * FROM ShowWatchListItem")
    suspend fun getShowWatchList(): List<ShowWatchListItem>

    @Query("SELECT id FROM MovieWatchListItem")
    fun observeMovieWatchList(): Flow<List<Long>>

    @Query("SELECT id FROM ShowWatchListItem")
    fun observeShowWatchList(): Flow<List<Long>>

    @Query("SELECT EXISTS(SELECT 1 FROM MovieWatchListItem WHERE id = :id)")
    suspend fun isMovieInWatchList(id: Long): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM ShowWatchListItem WHERE id = :id)")
    suspend fun isShowInWatchList(id: Long): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieWatchList(movieWatchList: List<MovieWatchListItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieWatchListItem: MovieWatchListItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShowWatchList(showWatchList: List<ShowWatchListItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShow(showWatchListItem: ShowWatchListItem)

    @Query("DELETE FROM MovieWatchListItem WHERE id = :id")
    suspend fun removeMovie(id: Long)

    @Query("DELETE FROM ShowWatchListItem WHERE id = :id")
    suspend fun removeShow(id: Long)

    @Query("DELETE FROM MovieWatchListItem")
    suspend fun clearMovies()

    @Query("DELETE FROM ShowWatchListItem")
    suspend fun clearShows()
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
