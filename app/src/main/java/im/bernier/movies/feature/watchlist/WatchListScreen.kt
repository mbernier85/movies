package im.bernier.movies.feature.watchlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet
import im.bernier.movies.component.MediaItem
import im.bernier.movies.feature.discover.toMediaUiStateItem
import im.bernier.movies.feature.movie.Movie
import im.bernier.movies.feature.movie.MovieRoute
import im.bernier.movies.navigation.EntryProviderInstaller
import im.bernier.movies.navigation.Navigator
import kotlinx.serialization.Serializable

@Serializable
data object WatchListRoute : Navigator.RequiresLogin

@Module
@InstallIn(ActivityRetainedComponent::class)
object WatchListModule {
    @IntoSet
    @Provides
    fun provideEntryProviderInstaller(navigator: Navigator): EntryProviderInstaller =
        {
            entry<WatchListRoute> {
                WatchListRoute(
                    onNavigateToMedia = { navigator.goTo(MovieRoute(it)) },
                )
            }
        }
}

@Composable
fun WatchListRoute(
    viewModel: WatchListViewModel = hiltViewModel(),
    onNavigateToMedia: (Long) -> Unit,
) {
    WatchListScreen(uiState = viewModel.uiState, onNavigateToMedia = onNavigateToMedia)
}

@Composable
fun WatchListScreen(
    uiState: UiState,
    onNavigateToMedia: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        MovieList(movies = uiState.movies, onNavigateToMedia = onNavigateToMedia)
    }
}

@Composable
fun MovieList(
    movies: List<Movie>,
    onNavigateToMedia: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
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
private fun WatchListScreenPreview() {
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
