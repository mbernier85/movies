package im.bernier.movies.feature.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.datasource.Repository
import javax.inject.Inject

/**
 * Created by Michael on 2020-01-22.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val searchResults = repository.searchResult

    fun submit(searchText: String) {
        repository.search(searchText)
    }

}