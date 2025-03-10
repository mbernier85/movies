package im.bernier.movies.feature.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.MovieRoute
import im.bernier.movies.datasource.Repository
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
    @Inject
    constructor(
        repository: Repository,
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val movieId: Long = (savedStateHandle.toRoute() as MovieRoute).id

        val movie =
            repository.fetchMovie(movieId).map {
                it.genreString =
                    it.genres.joinToString { genre ->
                        genre.name
                    }
                it
            }
    }
