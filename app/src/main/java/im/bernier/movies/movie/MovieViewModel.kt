package im.bernier.movies.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import im.bernier.movies.Repository

class MovieViewModel: ViewModel() {

    var movieId: Long = 0
        set(value) {
            Repository.fetchMovie(value)
            field = value
        }

    fun getLiveData(): LiveData<Movie> {
        return Repository.movieLiveData()
    }

}