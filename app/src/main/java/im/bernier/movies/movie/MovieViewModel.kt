package im.bernier.movies.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import im.bernier.movies.Repository
import im.bernier.movies.credits.Credits

class MovieViewModel: ViewModel() {

    var movieId: Long = 0
        set(value) {
            Repository.fetchMovie(value)
            field = value
        }

    var movie = Movie()

    fun getLiveData(): LiveData<Movie> {
        return Repository.movieLiveData()
    }

    fun getCreditsLiveData(): LiveData<Credits> {
        return Repository.creditsLiveData()
    }

    var genreString: String = ""
        get() {
            return movie.genres.joinToString { genre -> genre.name }
        }
}