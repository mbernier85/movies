package im.bernier.movies.feature.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.component.MediaUiStateItem
import im.bernier.movies.datasource.Repository
import im.bernier.movies.feature.discover.datasource.MoviesDataSource
import im.bernier.movies.feature.movie.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesDiscoverViewModel
    @Inject
    constructor(
        moviesDataSource: MoviesDataSource,
        private val repository: Repository,
    ) : ViewModel() {
        val watchListIds: Flow<Set<Long>> = repository.movieWatchListIds

        val pager =
            Pager(
                config = PagingConfig(20, initialLoadSize = 20),
                pagingSourceFactory = { moviesDataSource },
            ).flow.cachedIn(viewModelScope)
        private val isLoggedIn
            get() = repository.loggedIn

        fun onAddToWatchList(
            id: Long,
            mediaType: String,
            watchlist: Boolean,
            onLoginRequired: () -> Unit,
        ) {
            if (!isLoggedIn) {
                onLoginRequired()
                return
            }

            viewModelScope.launch {
                repository.updateWatchList(id, watchlist, mediaType)
            }
        }
    }

fun Movie.toMediaUiStateItem(watchlist: Boolean = watchList): MediaUiStateItem =
    MediaUiStateItem(
        id = id,
        title = title,
        overview = overview,
        posterPath = poster_path,
        genreString = genreString,
        watchlist = watchlist,
        mediaType = "movie",
    )
