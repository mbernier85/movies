package im.bernier.movies.feature.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.datasource.Repository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AccountViewModel
    @Inject
    constructor(
        private val repository: Repository,
    ) : ViewModel() {
        var uiState by mutableStateOf(UiState(name = ""))

        val exceptionHandler =
            CoroutineExceptionHandler { _, throwable ->
                Timber.e(throwable)
            }

        init {
            viewModelScope.launch {
                val account = repository.getAccount()
                uiState = uiState.copy(name = account.name.ifEmpty { account.username })
            }
        }

        fun logout() {
            viewModelScope.launch(exceptionHandler) {
                repository.logout()
            }
        }
    }

data class UiState(
    val name: String,
)
