package im.bernier.movies.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import im.bernier.movies.GlideApp
import im.bernier.movies.R

class MoviesAdapter(private val listener: (Long) -> Unit): PagedListAdapter<Movie, MoviesAdapter.MovieViewHolder>(
    MOVIE_COMPARATOR
) {

    companion object {
        val MOVIE_COMPARATOR = object: DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        val holder = MovieViewHolder(view)
        view.setOnClickListener {
            val movie = currentList?.get(holder.adapterPosition)
            if (movie != null) {
                listener.invoke(movie.id)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    class MovieViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            view.findViewById<TextView>(R.id.textViewMovieTitle).text = movie.title
            val imageView : ImageView = view.findViewById(R.id.imageViewMovie)
            val url = "https://image.tmdb.org/t/p/original" + movie.poster_path
            GlideApp.with(imageView).load(url).centerCrop().into(imageView)
            view.findViewById<TextView>(R.id.textViewMovieOverview).text = movie.overview
            view.findViewById<TextView>(R.id.textViewMovieGenres).text = movie.genreString
        }
    }
}