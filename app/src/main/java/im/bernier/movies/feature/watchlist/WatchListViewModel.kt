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
                storage.settingsFlow.collect { settings ->
                    val accountId = settings.accountId
                    val sessionId = settings.sessionId
                    val page =
                        repository.watchList(
                            accountId = accountId,
                            sessionId = sessionId,
                        )
                    val tvShow =
                        repository.watchListTV(
                            accountId = accountId,
                            sessionId = sessionId,
                        )
                    uiState = uiState.copy(movies = page.results, tvShow = tvShow.results)
                }
            }
        }
    }

data class UiState(
    val movies: List<Movie> = listOf(),
    val tvShow: List<TV> = listOf(),
)
