package im.bernier.movies

import androidx.room.Database
import androidx.room.RoomDatabase
import im.bernier.movies.genre.Genre
import im.bernier.movies.genre.GenreDao

@Database(entities = [Genre::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
}