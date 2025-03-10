package im.bernier.movies.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import im.bernier.movies.feature.genre.Genre
import im.bernier.movies.feature.genre.GenreDao

@Database(entities = [Genre::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
}
