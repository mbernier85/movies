package im.bernier.movies.feature.cast

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
import im.bernier.movies.di.AssistedViewModelFactory
import kotlinx.coroutines.launch

/**
 * Created by Michael on 2020-02-24.
 */
@HiltViewModel(assistedFactory = CastViewModel.ModelFactory::class)
class CastViewModel
    @AssistedInject
    constructor(
        @Assisted
        id: Long,
        val repository: Repository,
    ) : ViewModel() {
        @AssistedFactory
        fun interface ModelFactory : AssistedViewModelFactory<CastViewModel>

        var person by mutableStateOf(Person())
            private set

        init {
            viewModelScope.launch {
                person = repository.getCastById(id)
            }
        }
    }
