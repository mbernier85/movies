package im.bernier.movies.search

import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import im.bernier.movies.datasource.Repository
import im.bernier.movies.util.Keyboard
import javax.inject.Inject

/**
 * Created by Michael on 2020-01-22.
 */
class SearchViewModel @ViewModelInject constructor(private val repository: Repository): ViewModel() {

    var searchText = MutableLiveData<String>("")

    fun submit(view: View) {
        (view.parent as MotionLayout).transitionToEnd()
        Keyboard.hideKeyboard(view)
        repository.search(searchText.value ?: "")
    }

}