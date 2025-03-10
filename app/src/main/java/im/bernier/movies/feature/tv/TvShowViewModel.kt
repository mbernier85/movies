package im.bernier.movies.feature.tv

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.datasource.Repository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel
    @Inject
    constructor(
        repository: Repository,
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val movieId: Long = (savedStateHandle.toRoute() as TvShowRoute).id
        val tvShow = repository.fetchTv(movieId).doOnError { Timber.e(it) }
    }
