package im.bernier.movies.feature.movie

import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.datasource.Repository

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

    val movie =
        repository.fetchMovie(id).map {
            it.genreString =
                it.genres.joinToString { genre ->
                    genre.name
                }
            it
        }
}
