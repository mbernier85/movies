package im.bernier.movies.search

import android.view.View
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import im.bernier.movies.datasource.Repository
import im.bernier.movies.util.Keyboard

/**
 * Created by Michael on 2020-01-22.
 */
class SearchViewModel: ViewModel() {

    var searchText = MutableLiveData<String>("")

    fun submit(view: View) {
        (view.parent as MotionLayout).transitionToEnd()
        Keyboard.hideKeyboard(view)
        Repository.search(searchText.value ?: "")
    }

}