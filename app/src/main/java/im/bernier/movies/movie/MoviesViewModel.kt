package im.bernier.movies.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesDataSource: MoviesDataSource
) : ViewModel() {

    private val _errors = MutableLiveData<Throwable>()
    val pager = Pager(
        config = PagingConfig(20, initialLoadSize = 20)
    ) {
        moviesDataSource
    }

    val errors
        get() = _errors

    fun search() {

    }
}