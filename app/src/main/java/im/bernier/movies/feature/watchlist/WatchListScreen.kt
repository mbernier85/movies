package im.bernier.movies.feature.watchlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import im.bernier.movies.component.MediaItem
import im.bernier.movies.feature.discover.toMediaUiStateItem
import im.bernier.movies.feature.movie.Movie
import kotlinx.serialization.Serializable

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
