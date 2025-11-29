package im.bernier.movies.feature.watchlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.datasource.Repository
import im.bernier.movies.datasource.local.Storage
import im.bernier.movies.feature.movie.Movie
import im.bernier.movies.feature.tv.TV
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel
    @Inject
    constructor(
        repository: Repository,
        storage: Storage,
    ) : ViewModel() {
        var uiState by mutableStateOf(UiState())
        val exceptionHandler =
            CoroutineExceptionHandler { _, throwable ->
                Timber.e(throwable)
            }

        init {
            viewModelScope.launch(exceptionHandler) {
                val page =
                    repository.watchList(
                        accountId = storage.getAccountId(),
                        sessionId = storage.getSessionId(),
                    )
                uiState = uiState.copy(movies = page.results)

                val tvShow =
                    repository.watchListTV(
                        accountId = storage.getAccountId(),
                        sessionId = storage.getSessionId(),
                    )
                uiState = uiState.copy(tvShow = tvShow.results)
            }
        }
    }

data class UiState(
    val movies: List<Movie> = listOf(),
    val tvShow: List<TV> = listOf(),
)
