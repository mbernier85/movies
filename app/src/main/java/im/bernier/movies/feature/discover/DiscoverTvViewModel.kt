package im.bernier.movies.feature.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.component.MediaUiStateItem
import im.bernier.movies.datasource.Repository
import im.bernier.movies.feature.discover.datasource.TVDataSource
import im.bernier.movies.feature.tv.TV
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DiscoverTvViewModel
    @Inject
    constructor(
        tvDataSource: TVDataSource,
        private val repository: Repository,
    ) : ViewModel() {
        val watchListIds: Flow<Set<Long>> = repository.showWatchListIds

        val pager =
            Pager(
                config = PagingConfig(20, initialLoadSize = 20),
            ) {
                tvDataSource
            }

        val pagerFlow = pager.flow.cachedIn(viewModelScope)

        val handler =
            CoroutineExceptionHandler { _, throwable ->
                when (throwable) {
                    is HttpException -> {
                        if (throwable.code() == 401) {
                            // Navigate to login
                        }
                    }
                }
                Timber.e(throwable)
            }

        fun onAddToWatchList(
            id: Long,
            mediaType: String,
            watchlist: Boolean,
            onLoginRequired: () -> Unit,
        ) {
            if (!repository.loggedIn) {
                onLoginRequired()
                return
            }

            viewModelScope.launch(handler) {
                repository.updateWatchList(mediaId = id, watchlist = watchlist, mediaType = mediaType)
            }
        }
    }

fun TV.toMediaUiStateItem(watchlist: Boolean = watchList): MediaUiStateItem =
    MediaUiStateItem(
        id = id,
        title = name,
        overview = overview,
        posterPath = poster_path,
        genreString = genreString,
        watchlist = watchlist,
        mediaType = "tv",
    )
