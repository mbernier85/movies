package im.bernier.movies.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.toLiveData
import im.bernier.movies.datasource.Repository
import javax.inject.Inject

class MoviesViewModel @ViewModelInject constructor(repository: Repository): ViewModel() {

    var moviesDataSource: MoviesDataSource? = null
    private val _errors = MutableLiveData<Throwable>()
    val sourceFactory = MoviesDataSourceFactory(_errors, repository)
    val liveData = sourceFactory.toLiveData(pageSize = 20, initialLoadKey = 1)

    val errors
        get() = _errors

    fun refresh() {
        moviesDataSource?.invalidate()
    }
}