package im.bernier.movies.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("android:src")
fun src(view: ImageView, url: String?) {
    url?.let {
        Glide.with(view.context).load(it.imageUrl()).dontTransform().into(view)
    }
}