package im.bernier.movies.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.datasource.Repository
import im.bernier.movies.movieIdArg
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    repository: Repository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val movieId: String = checkNotNull(savedStateHandle[movieIdArg])

    val movie =
        repository.fetchMovie(movieId.toLong()).map {
            it.genreString = it.genres.joinToString { genre ->
                genre.name
            }
            it
        }

}