package im.bernier.movies.feature.genre

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.serialization.Serializable

@Dao
interface GenreDao {
    @Query("SELECT * FROM genre")
    fun getAll(): LiveData<List<Genre>>

    @Query("SELECT * FROM genre WHERE id IN (:genreIds)")
    suspend fun loadAllByIds(genreIds: IntArray): List<Genre>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(genres: List<Genre>)
}

@Serializable
data class Genres(
    val genres: List<Genre>,
)

@Entity
@Serializable
data class Genre(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
)
