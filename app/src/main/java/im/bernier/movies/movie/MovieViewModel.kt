package im.bernier.movies.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import im.bernier.movies.credits.Credits
import im.bernier.movies.datasource.Repository

class MovieViewModel: ViewModel() {

    var movieId: Int = 0
        set(value) {
            Repository.fetchMovie(value)
            field = value
        }

    var movie = Movie()

    fun getLiveData(): LiveData<Movie> {
        return Repository.movie(movieId)
    }

    fun getCreditsLiveData(): LiveData<Credits> {
        return Repository.credits
    }

    val genreString: String
        get() {
            return movie.genres.joinToString { genre -> genre.name }
        }
}