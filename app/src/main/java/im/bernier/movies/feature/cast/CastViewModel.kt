package im.bernier.movies.feature.cast

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.CastRoute
import im.bernier.movies.datasource.Repository
import javax.inject.Inject

/**
 * Created by Michael on 2020-02-24.
 */
@HiltViewModel
class CastViewModel
    @Inject
    constructor(
        val repository: Repository,
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val personId: Long = (savedStateHandle.toRoute() as CastRoute).id
        val person = repository.api.getCastById(personId)
    }
