package im.bernier.movies.feature.tv

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.datasource.Repository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel(assistedFactory = TvShowViewModel.ShowViewModelFactory::class)
class TvShowViewModel
@AssistedInject
constructor(
    repository: Repository,
    @Assisted
    id: Long,
) : ViewModel() {
    @AssistedFactory
    interface ShowViewModelFactory {
        fun create(id: Long): TvShowViewModel
    }

    var tvShow by mutableStateOf(TV())
        private set

    val handler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
    }

    init {
        viewModelScope.launch(handler) {
            tvShow = repository.fetchTv(id)
        }

    }
}
