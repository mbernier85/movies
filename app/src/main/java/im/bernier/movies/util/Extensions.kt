package im.bernier.movies.util

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showError(error: String) {
    Snackbar.make(this.requireView(), error, Snackbar.LENGTH_LONG).show()
}

fun Fragment.showError(@StringRes error: Int) {
    showError(this.resources.getString(error))
}