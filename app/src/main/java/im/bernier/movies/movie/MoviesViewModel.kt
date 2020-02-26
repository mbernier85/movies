package im.bernier.movies.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.toLiveData

class MoviesViewModel: ViewModel() {

    var moviesDataSource: MoviesDataSource? = null
    private val _errors = MutableLiveData<Throwable>()
    val sourceFactory = MoviesDataSourceFactory(_errors)
    val liveData = sourceFactory.toLiveData(pageSize = 20, initialLoadKey = 1)

    val errors
        get() = _errors

    fun refresh() {
        moviesDataSource?.invalidate()
    }
}