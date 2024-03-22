package im.bernier.movies.feature.tv

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.datasource.Repository
import im.bernier.movies.movieIdArg
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle
):ViewModel() {
    private val movieId: Long = checkNotNull(savedStateHandle[movieIdArg])
    val tvShow = repository.fetchTv(movieId).doOnError { Timber.e(it) }

}