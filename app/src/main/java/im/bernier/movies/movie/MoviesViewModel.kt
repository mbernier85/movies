package im.bernier.movies.movie

import androidx.lifecycle.ViewModel
import androidx.paging.toLiveData

class MoviesViewModel: ViewModel() {

    var moviesDataSource: MoviesDataSource? = null
    val sourceFactory = MoviesDataSourceFactory()
    val liveData = sourceFactory.toLiveData(pageSize = 20, initialLoadKey = 1)

    fun refresh() {
        moviesDataSource?.invalidate()
    }
}