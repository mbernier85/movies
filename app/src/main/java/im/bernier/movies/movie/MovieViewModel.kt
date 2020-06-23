package im.bernier.movies.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import im.bernier.movies.credits.Credits
import im.bernier.movies.datasource.Repository
import javax.inject.Inject

class MovieViewModel @ViewModelInject constructor(private val repository: Repository): ViewModel() {

    var movieId: Long = 0
        set(value) {
            repository.fetchMovie(value)
            field = value
        }

    var movie = Movie()

    fun getLiveData(): LiveData<Movie> {
        return repository.movie(movieId)
    }

    fun getCreditsLiveData(): LiveData<Credits> {
        return repository.credits
    }

    val genreString: String
        get() {
            return movie.genres.joinToString { genre -> genre.name }
        }
}