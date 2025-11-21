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

    val pager =
        Pager(
            config = PagingConfig(20, initialLoadSize = 20),
        ) {
            tvDataSource
        }

    val pagerFlow = pager.flow.cachedIn(viewModelScope)

    val handler = CoroutineExceptionHandler { _, throwable ->
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
    ) {
        viewModelScope.launch(handler) {
            repository.addToWatchList(mediaId = id, watchlist = true, mediaType = mediaType)
        }
    }
}

fun TV.toMediaUiStateItem(): MediaUiStateItem =
    MediaUiStateItem(
        id = id,
        title = name,
        overview = overview,
        posterPath = poster_path,
        genreString = genreString,
        watchlist = false,
        mediaType = "tv",
    )
