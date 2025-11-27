package im.bernier.movies.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.datasource.Repository
import im.bernier.movies.navigation.EntryProviderInstaller
import im.bernier.movies.navigation.Navigator
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val repository: Repository,
        val navigator: Navigator,
        val entryProviderScope: Set<@JvmSuppressWildcards EntryProviderInstaller>,
    ) : ViewModel() {
        val loggedIn: Boolean
            get() = repository.loggedIn

        val handler =
            CoroutineExceptionHandler { _, throwable ->
                Timber.e(throwable)
            }

        init {
            viewModelScope.launch(handler) {
                repository.fetchGenres()
            }
        }
    }
