package im.bernier.movies.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.credits.Credits
import im.bernier.movies.datasource.Repository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    var movieId: Long = 0
    private var _movie = Movie()

    fun observableMovie(): Observable<Movie> {
        return repository.fetchMovie(movieId)
    }

    var movie: Movie = _movie
        set(value) {
            _movie = value
            field = value
        }

    fun getCreditsLiveData(): LiveData<Credits> {
        return repository.credits
    }

    val genreString: String
        get() {
            return _movie.genres.joinToString { genre -> genre.name }
        }
}