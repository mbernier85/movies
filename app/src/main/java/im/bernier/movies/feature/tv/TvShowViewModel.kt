package im.bernier.movies.feature.tv

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.datasource.Repository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(
    repository: Repository,
    savedStateHandle: SavedStateHandle
):ViewModel() {
    private val movieId: String = checkNotNull(savedStateHandle[tvShowIdArg])
    val tvShow = repository.fetchTv(movieId.toLong()).doOnError { Timber.e(it) }

}