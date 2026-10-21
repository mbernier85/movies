package im.bernier.movies.feature.watchlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet
import im.bernier.movies.component.Actions
import im.bernier.movies.component.MediaItem
import im.bernier.movies.component.MediaUiStateItem
import im.bernier.movies.feature.discover.toMediaUiStateItem
import im.bernier.movies.feature.movie.Movie
import im.bernier.movies.navigation.EntryProviderInstaller
import im.bernier.movies.navigation.Navigator
import kotlinx.coroutines.launch
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
                    onNavigateToMedia = navigator::goTo,
                    onNavigateToShow = navigator::goTo,
                )
            }
        }
}

@Composable
fun WatchListRoute(
    onNavigateToMedia: (Long) -> Unit,
    onNavigateToShow: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WatchListViewModel = hiltViewModel(),
) {
    val tabs = listOf("Movies", "Tv Shows")
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val scope = rememberCoroutineScope()
    val movieWatchListIds by viewModel.movieWatchListIds.collectAsState(initial = emptySet())
    val showWatchListIds by viewModel.showWatchListIds.collectAsState(initial = emptySet())
    Column(modifier = modifier) {
        PrimaryTabRow(
            selectedTabIndex = pagerState.currentPage,
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text = title) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                )
            }
        }
        HorizontalPager(
            state = pagerState,
        ) { index ->
            when (index) {
                0 -> {
                    WatchListScreen(
                        mediaUiStateItems =
                            viewModel.uiState.movies
                                .filter { it.id in movieWatchListIds }
                                .map { it.toMediaUiStateItem(watchlist = true) },
                        onNavigateToMedia = onNavigateToMedia,
                        onWatchListToggle = viewModel::onWatchListToggle,
                    )
                }

                1 -> {
                    WatchListScreen(
                        mediaUiStateItems =
                            viewModel.uiState.tvShow
                                .filter { it.id in showWatchListIds }
                                .map { it.toMediaUiStateItem(watchlist = true) },
                        onNavigateToMedia = onNavigateToShow,
                        onWatchListToggle = viewModel::onWatchListToggle,
                    )
                }
            }
        }
    }
}

@Composable
fun WatchListScreen(
    mediaUiStateItems: List<MediaUiStateItem>,
    onNavigateToMedia: (Long) -> Unit,
    onWatchListToggle: (Long, String, Boolean) -> Unit = { _, _, _ -> },
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        MovieList(
            mediaUiStateItems = mediaUiStateItems,
            onNavigateToMedia = onNavigateToMedia,
            onWatchListToggle = onWatchListToggle,
        )
    }
}

@Composable
fun MovieList(
    mediaUiStateItems: List<MediaUiStateItem>,
    onNavigateToMedia: (Long) -> Unit,
    onWatchListToggle: (Long, String, Boolean) -> Unit = { _, _, _ -> },
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(mediaUiStateItems) {
            MediaItem(
                item = it,
                actions =
                    Actions(
                        onMediaClick = onNavigateToMedia,
                        onAddToWatchList = onWatchListToggle,
                    ),
            )
        }
    }
}

@Composable
@Preview
private fun WatchListScreenPreview() {
    val uiState =
        UiState(
            listOf(
                Movie(
                    title = "The Matrix",
                    overview = LoremIpsum(20).values.joinToString(),
                ),
            ),
        )
    WatchListScreen(
        mediaUiStateItems = uiState.movies.map { it.toMediaUiStateItem() },
        onNavigateToMedia = {},
    )
}
