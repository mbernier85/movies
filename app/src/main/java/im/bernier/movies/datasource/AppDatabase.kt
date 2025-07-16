package im.bernier.movies.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import im.bernier.movies.feature.genre.Genre
import im.bernier.movies.feature.genre.GenreDao
import im.bernier.movies.feature.watchlist.MovieWatchListItem
import im.bernier.movies.feature.watchlist.ShowWatchListItem
import im.bernier.movies.feature.watchlist.WatchListDAO

@Database(
    entities = [
        Genre::class,
        MovieWatchListItem::class,
        ShowWatchListItem::class,
    ],
    version = 2,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao

    abstract fun watchListDao(): WatchListDAO
}
