package im.bernier.movies.util

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager


class Keyboard {
    companion object {
        fun hideKeyboard(view: View) {
            val imm =
                view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

