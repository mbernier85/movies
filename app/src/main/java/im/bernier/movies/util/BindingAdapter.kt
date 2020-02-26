package im.bernier.movies.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import im.bernier.movies.GlideApp

@BindingAdapter("android:src")
fun src(view: ImageView, url: String?) {
    url?.let {
        GlideApp.with(view.context).load(imageUrl(it)).dontTransform().into(view)
    }
}