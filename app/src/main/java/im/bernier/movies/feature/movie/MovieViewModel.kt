package im.bernier.movies.feature.movie

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.datasource.Repository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel(assistedFactory = MovieViewModel.MovieViewModelFactory::class)
class MovieViewModel
    @AssistedInject
    constructor(
        @Assisted
        id: Long,
        repository: Repository,
    ) : ViewModel() {
        @AssistedFactory
        interface MovieViewModelFactory {
            fun create(id: Long): MovieViewModel
        }

        var movieState by mutableStateOf(Movie())

        val handler =
            CoroutineExceptionHandler { _, throwable ->
                Timber.e(throwable)
            }

        init {
            viewModelScope.launch(handler) {
                val movie = repository.fetchMovie(id)
                movie.genreString =
                    movie.genres.joinToString { genre ->
                        genre.name
                    }
                movieState = movie
            }
        }
    }
