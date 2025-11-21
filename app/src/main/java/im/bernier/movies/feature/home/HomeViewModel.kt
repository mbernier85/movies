package im.bernier.movies.feature.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.DiscoverRoute
import im.bernier.movies.datasource.Repository
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val repository: Repository,
) : ViewModel() {
    val loggedIn: Boolean
        get() = repository.loggedIn
    val backStack = mutableStateListOf<Any>(DiscoverRoute)

    val handler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
    }

    init {
        viewModelScope.launch(handler) {
            repository.fetchGenres()
        }
    }
}
