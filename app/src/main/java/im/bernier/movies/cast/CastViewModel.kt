package im.bernier.movies.cast

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.castIdArg
import im.bernier.movies.datasource.Repository
import javax.inject.Inject

/**
 * Created by Michael on 2020-02-24.
 */
@HiltViewModel
class CastViewModel @Inject constructor(
    val repository: Repository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val personId: String = checkNotNull(savedStateHandle[castIdArg])
    val person = repository.api.getCastById(personId.toLong())
}