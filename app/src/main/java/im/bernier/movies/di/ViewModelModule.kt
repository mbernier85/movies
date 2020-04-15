package im.bernier.movies.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import im.bernier.movies.cast.CastViewModel
import im.bernier.movies.movie.MovieViewModel
import im.bernier.movies.movie.MoviesViewModel
import im.bernier.movies.search.SearchViewModel

/**
 * Created by Michael on 2020-04-03.
 */
@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    abstract fun bindMoviesViewModel(moviesViewModel: MoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    abstract fun bindMovieViewModel(movieViewModel: MovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CastViewModel::class)
    abstract fun bindCastViewModel(castViewModel: CastViewModel): ViewModel
}