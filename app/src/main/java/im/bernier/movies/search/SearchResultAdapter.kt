package im.bernier.movies.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import im.bernier.movies.BaseAdapter
import im.bernier.movies.GlideApp
import im.bernier.movies.R
import im.bernier.movies.util.imageUrl

/**
 * Created by Michael on 2020-01-23.
 */
class SearchResultAdapter(list: List<SearchResultItem>, private val listener: (SearchResultItem) -> Unit) :
    BaseAdapter<SearchResultItem, SearchResultAdapter.SearchItemViewHolder>(list) {
    override fun update(list: List<SearchResultItem>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<SearchResultItem> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        val holder = SearchItemViewHolder(view)
        view.setOnClickListener {
            listener.invoke(list[holder.bindingAdapterPosition])
        }
        return holder
    }

    class SearchItemViewHolder(val view: View) :
        BaseViewHolder<SearchResultItem>(view) {
        override fun bind(item: SearchResultItem) {
            view.findViewById<TextView>(R.id.textViewMovieTitle).text = item.title ?: item.name

            val imageView : ImageView = view.findViewById(R.id.imageViewMovie)
            val url = imageUrl(item.poster_path ?: item.profile_path ?: "")
            GlideApp.with(imageView).load(url).dontTransform().into(imageView)
        }
    }
}