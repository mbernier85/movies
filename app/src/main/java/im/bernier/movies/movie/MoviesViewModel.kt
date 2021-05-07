package im.bernier.movies.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.toLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.datasource.Repository
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(repository: Repository): ViewModel() {

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