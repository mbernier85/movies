package im.bernier.movies.feature.account

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.datasource.Repository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    repository: Repository
): ViewModel() {
    val uiState: Single<UiState> = repository.getAccount().map {
        val uiState = UiState(
            it.username
        )
        uiState
    }
}

data class UiState(val name: String)