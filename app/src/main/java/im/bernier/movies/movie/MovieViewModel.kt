package im.bernier.movies.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.credits.Credits
import im.bernier.movies.datasource.Repository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val movieId: String = checkNotNull(savedStateHandle["movieId"])

    fun observableMovie(): Observable<Movie> {
        return repository.fetchMovie(movieId.toLong()).map {
            it.genreString = it.genres.joinToString { genre ->
                genre.name
            }
            it
        }
    }

    fun getCreditsLiveData(): LiveData<Credits> {
        return repository.credits
    }
}