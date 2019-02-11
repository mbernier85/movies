package im.bernier.movies.genre

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GenreDao {
    @Query("SELECT * FROM genre")
    fun getAll(): LiveData<List<Genre>>

    @Query("SELECT * FROM genre WHERE id IN (:genreIds)")
    fun loadAllByIds(genreIds: IntArray): List<Genre>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(genres: List<Genre>)
}