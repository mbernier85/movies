package im.bernier.movies.feature.watchlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import im.bernier.movies.R
import im.bernier.movies.component.MediaItem
import im.bernier.movies.feature.discover.toMediaUiStateItem
import im.bernier.movies.feature.movie.Movie
import kotlinx.serialization.Serializable

@Serializable
data object WatchListRoute

fun NavController.navigateToWatchList() = this.navigate(WatchListRoute)

fun NavGraphBuilder.watchListRoute(onTitleChanged: (String) -> Unit) =
    composable<WatchListRoute> {
        onTitleChanged(stringResource(R.string.watch_list_title))
        WatchListRoute()
    }

@Composable
fun WatchListRoute(viewModel: WatchListViewModel = hiltViewModel()) {
    WatchListScreen(uiState = viewModel.uiState, onNavigateToMedia = { })
}

@Composable
fun WatchListScreen(
    uiState: UiState,
    onNavigateToMedia: (Long) -> Unit,
) {
    Column {
        MovieList(movies = uiState.movies, onNavigateToMedia = onNavigateToMedia)
    }
}

@Composable
fun MovieList(
    movies: List<Movie>,
    onNavigateToMedia: (Long) -> Unit,
) {
    LazyColumn {
        items(movies) {
            MediaItem(
                item = it.toMediaUiStateItem(),
                onNavigateToMedia = onNavigateToMedia,
            )
        }
    }
}

@Composable
@Preview
fun WatchListScreenPreview() {
    WatchListScreen(
        uiState =
            UiState(
                listOf(
                    Movie(
                        title = "The Matrix",
                        overview = LoremIpsum(20).values.joinToString(),
                    ),
                ),
            ),
        onNavigateToMedia = {},
    )
}
