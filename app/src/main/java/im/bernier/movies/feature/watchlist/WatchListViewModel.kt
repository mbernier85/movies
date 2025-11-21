package im.bernier.movies.feature.watchlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.datasource.Repository
import im.bernier.movies.datasource.Storage
import im.bernier.movies.feature.movie.Movie
import im.bernier.movies.feature.tv.TV
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel
@Inject
constructor(
    repository: Repository,
    storage: Storage,
) : ViewModel() {
    var uiState by mutableStateOf(UiState())

    init {
        viewModelScope.launch {
            val page = repository.watchList(
                accountId = storage.getAccountId(),
                sessionId = storage.getSessionId()
            )
            uiState = uiState.copy(movies = page.results)
        }
    }
}

data class UiState(
    val movies: List<Movie> = listOf(),
    val tvShow: List<TV> = listOf(),
)
