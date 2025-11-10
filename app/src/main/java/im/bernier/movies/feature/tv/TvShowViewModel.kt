package im.bernier.movies.feature.tv

import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.datasource.Repository
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
        val tvShow = repository.fetchTv(id).doOnError { Timber.e(it) }
    }
