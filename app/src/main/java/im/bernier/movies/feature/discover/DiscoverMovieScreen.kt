package im.bernier.movies.feature.discover

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import im.bernier.movies.component.Actions
import im.bernier.movies.component.MediaItem
import im.bernier.movies.feature.movie.Movie
import timber.log.Timber

@Composable
fun DiscoverMovieScreen(
    viewModel: MoviesDiscoverViewModel = hiltViewModel(),
    onNavigateToMovie: (Long) -> Unit,
) {
    val items = viewModel.pager.collectAsLazyPagingItems()

    MediaList(
        lazyPagingItems = items,
        onNavigateToMovie = onNavigateToMovie,
        onAddToWatchList = { id, mediaType ->
            viewModel.onAddToWatchList(id, mediaType)
        },
    )
}

@Composable
fun MediaList(
    lazyPagingItems: LazyPagingItems<Movie>,
    onNavigateToMovie: (Long) -> Unit,
    onAddToWatchList: (Long, String) -> Unit,
) {
    when (val loadState = lazyPagingItems.loadState.refresh) {
        is LoadState.Error -> {
            Timber.e(loadState.error)
        }
        LoadState.Loading -> {}
        is LoadState.NotLoading -> {}
    }
    LazyColumn(
        modifier =
            Modifier
                .padding(4.dp)
                .fillMaxWidth(),
    ) {
        items(
            count = lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { it.id },
        ) { index ->
            val movie = lazyPagingItems[index]
            if (movie != null) {
                MediaItem(
                    item = movie.toMediaUiStateItem(),
                    actions = Actions(
                        onMediaClick = onNavigateToMovie,
                        onAddToWatchList = onAddToWatchList
                    )
                )
            }
        }
    }
}
