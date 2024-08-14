package im.bernier.movies.feature.discover

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import im.bernier.movies.feature.authentication.navigateToLogin
import im.bernier.movies.feature.movie.Movie
import im.bernier.movies.navigateToMovie
import kotlinx.coroutines.flow.Flow

@Composable
fun DiscoverMovieScreen(
    viewModel: MoviesDiscoverViewModel = hiltViewModel(),
    navController: NavController
) {
    val pager: Flow<PagingData<Movie>> = viewModel.pager

    MediaList(
        pager = pager,
        onNavigateToMovie = {
            navController.navigateToMovie(it)
        },
        onAddToWatchList = { id, mediaType ->
            if (viewModel.isLoggedIn) {
                viewModel.onAddToWatchList(id, mediaType)
            } else {
                navController.navigateToLogin()
            }
        }
    )
}

@Composable
fun MediaList(
    pager: Flow<PagingData<Movie>>,
    onNavigateToMovie: (Long) -> Unit,
    onAddToWatchList: (Long, String) -> Unit,

) {
    val lazyPagingItems = pager.collectAsLazyPagingItems()
    LazyColumn(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        items(
            count = lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { it.id },
        ) { index ->
            val movie = lazyPagingItems[index]
            if (movie != null) {
                MediaItem(
                    item = movie.toMediaUiStateItem(),
                    onNavigateToMovie,
                    onAddToWatchList = onAddToWatchList
                )
            }
        }
    }
}
