package im.bernier.movies.feature.watchlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.datasource.Repository
import im.bernier.movies.datasource.Storage
import im.bernier.movies.feature.movie.Movie
import im.bernier.movies.feature.tv.TV
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel
    @Inject
    constructor(
        private val repository: Repository,
        private val storage: Storage,
    ) : ViewModel() {
        var uiState by mutableStateOf(UiState())
        private val compositeDisposable = CompositeDisposable()

        init {
            val disposable =
                repository.watchList(accountId = storage.getAccountId(), sessionId = storage.getSessionId()).subscribe({
                    uiState = uiState.copy(movies = it.results)
                }, {
                    Timber.e(it)
                })
            compositeDisposable.add(disposable)
        }

        override fun onCleared() {
            compositeDisposable.clear()
            super.onCleared()
        }
    }

data class UiState(
    val movies: List<Movie> = listOf(),
    val tvShow: List<TV> = listOf(),
)
