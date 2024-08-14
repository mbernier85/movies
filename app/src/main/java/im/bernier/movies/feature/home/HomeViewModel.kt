package im.bernier.movies.feature.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.datasource.Repository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val uiState = mutableStateOf(UiState())

    fun openAccount() {
        if (repository.loggedIn) {
            uiState.value = uiState.value.copy(openAccount = true)
        } else {
            uiState.value = uiState.value.copy(openLogin = true)
        }
    }
}

data class UiState(
    val openAccount: Boolean = false,
    val openLogin: Boolean = false
)