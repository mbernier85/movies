package im.bernier.movies.feature.cast

import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.datasource.Repository
import im.bernier.movies.di.AssistedViewModelFactory

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
        fun interface ModelFactory: AssistedViewModelFactory<CastViewModel>
        val person = repository.api.getCastById(id)
    }
