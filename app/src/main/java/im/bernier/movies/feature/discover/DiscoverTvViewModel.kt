package im.bernier.movies.feature.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.datasource.Repository
import im.bernier.movies.feature.tv.TV
import im.bernier.movies.feature.tv.TVDataSource
import io.reactivex.rxjava3.disposables.CompositeDisposable
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
        private val compositeDisposable = CompositeDisposable()

        val pager =
            Pager(
                config = PagingConfig(20, initialLoadSize = 20),
            ) {
                tvDataSource
            }.flow.cachedIn(viewModelScope)

        fun onAddToWatchList(
            id: Long,
            mediaType: String,
        ) {
            compositeDisposable.add(
                repository
                    .addToWatchList(id, true, mediaType)
                    .subscribe(
                        {
                            // Do nothing for now
                        },
                        {
                            Timber.e(it)
                            if (it is HttpException) {
                                if (it.code() == 401) {
                                    // uiState.value = uiState.value.copy(navigateToLogin = true)
                                }
                            }
                        },
                    ),
            )
        }

        override fun onCleared() {
            compositeDisposable.clear()
            super.onCleared()
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
