package im.bernier.movies.di

import android.app.Application
import dagger.Component
import im.bernier.movies.MainActivity
import im.bernier.movies.cast.CastFragment
import im.bernier.movies.datasource.Repository
import im.bernier.movies.movie.MovieFragment
import im.bernier.movies.movie.MoviesFragment
import im.bernier.movies.search.SearchFragment
import javax.inject.Singleton

/**
 * Created by Michael on 2020-03-14.
 */
@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {
    fun provideViewModelFactory(): ViewModelFactory
    fun provideRepository(): Repository

    fun inject(application: Application)
    fun inject(mainActivity: MainActivity)
    fun inject(castFragment: CastFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(moviesFragment: MoviesFragment)
    fun inject(movieFragment: MovieFragment)
}