package im.bernier.movies.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import im.bernier.movies.feature.genre.Genre
import im.bernier.movies.feature.genre.GenreDao
import im.bernier.movies.feature.watchlist.MovieWatchListItem
import im.bernier.movies.feature.watchlist.ShowWatchListItem
import im.bernier.movies.feature.watchlist.WatchListDAO
import jakarta.inject.Singleton
import kotlinx.coroutines.Dispatchers

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

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDb(
        @ApplicationContext context: Context,
    ): AppDatabase =
        Room
            .databaseBuilder(context.applicationContext, AppDatabase::class.java, "movies")
            .fallbackToDestructiveMigration(false)
            .setQueryCoroutineContext(Dispatchers.Default)
            .build()
}
