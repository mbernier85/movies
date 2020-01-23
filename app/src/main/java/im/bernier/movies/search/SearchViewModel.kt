package im.bernier.movies.search

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import im.bernier.movies.datasource.Repository

/**
 * Created by Michael on 2020-01-22.
 */
class SearchViewModel: ViewModel() {

    var searchText = MutableLiveData<String>()

    fun submit(view: View) {
        Repository.search(searchText.value ?: "")
    }

}