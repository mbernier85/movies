package im.bernier.movies.feature.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.datasource.Repository
import im.bernier.movies.feature.movie.Movie
import im.bernier.movies.feature.movie.MoviesDataSource
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MoviesDiscoverViewModel @Inject constructor(
    moviesDataSource: MoviesDataSource,
    private val repository: Repository,
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val pager = Pager(
        config = PagingConfig(20, initialLoadSize = 20)
    ) {
        moviesDataSource
    }.flow.cachedIn(viewModelScope)

    val isLoggedIn = repository.loggedIn

    fun onAddToWatchList(id: Long, mediaType: String) {
        compositeDisposable.add(repository.addToWatchList(id, true, mediaType)
            .subscribe({
                // Do nothing for now
            }, {
                Timber.e(it)
                if (it is HttpException) {
                    if (it.code() == 401) {
                        navigateToLogin()
                    }
                }
            })
        )
    }

    private fun navigateToLogin() {

    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}

data class Actions(
    val navigateToLogin: () -> Unit
)

fun Movie.toMediaUiStateItem(): MediaUiStateItem {
    return MediaUiStateItem(
        id = id,
        title = title,
        overview = overview,
        posterPath = poster_path,
        genreString = genreString,
        watchlist = false,
        mediaType = "movie"
    )
}