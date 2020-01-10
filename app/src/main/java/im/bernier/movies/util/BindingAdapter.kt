package im.bernier.movies.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:src")
fun src(view: ImageView, url: String) {
    GlideApp.with(view.context).load(imageUrl(url)).dontTransform().into(view)
}