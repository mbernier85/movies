package im.bernier.movies.feature.authentication

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
class LoginViewModel
    @Inject
    constructor(
        val repository: Repository,
    ) : ViewModel() {
        var uiState by mutableStateOf(UiState())

        val handler =
            CoroutineExceptionHandler { _, exception ->
                Timber.e(exception)
                uiState = uiState.copy(success = false)
            }

        fun login(
            username: String,
            password: String,
        ) {
            viewModelScope.launch(context = handler) {
                repository
                    .login()
                    .also {
                        repository.validateToken(it.request_token, username, password)
                    }.also {
                        repository.newSession(it.request_token)
                    }.also {
                        repository.getAccount()
                    }.also {
                        uiState = uiState.copy(success = it.success)
                    }
            }
        }
    }

data class UiState(
    val success: Boolean = false,
)
