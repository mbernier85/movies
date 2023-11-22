package im.bernier.movies.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.datasource.Repository
import javax.inject.Inject

/**
 * Created by Michael on 2020-01-22.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var searchText = MutableLiveData("")

    fun submit(): LiveData<List<SearchResultItem>> {
        repository.search(searchText.value ?: "")
        return repository.searchResult
    }

}