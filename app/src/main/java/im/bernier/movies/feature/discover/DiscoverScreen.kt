package im.bernier.movies.feature.discover

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet
import im.bernier.movies.R
import im.bernier.movies.feature.account.AccountRoute
import im.bernier.movies.feature.movie.MovieRoute
import im.bernier.movies.feature.search.SearchRoute
import im.bernier.movies.feature.tv.ShowRoute
import im.bernier.movies.feature.watchlist.WatchListRoute
import im.bernier.movies.navigation.EntryProviderInstaller
import im.bernier.movies.navigation.Navigator
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data object DiscoverRoute

@Module
@InstallIn(ActivityRetainedComponent::class)
object DiscoverModule {
    @IntoSet
    @Provides
    fun provideEntryProviderInstaller(navigator: Navigator): EntryProviderInstaller =
        {
            entry<DiscoverRoute> {
                DiscoverScreen(
                    onNavigateToMovie = {
                        navigator.goTo(MovieRoute(it))
                    },
                    onNavigateToShow = {
                        navigator.goTo(ShowRoute(it))
                    },
                    onAccountTap = {
                        navigator.goTo(AccountRoute)
                    },
                    onSearchTap = {
                        navigator.goTo(SearchRoute)
                    },
                    onWatchListTap = {
                        navigator.goTo(WatchListRoute)
                    },
                )
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(
    onNavigateToMovie: (Long) -> Unit,
    onNavigateToShow: (Long) -> Unit,
    onAccountTap: () -> Unit,
    onSearchTap: () -> Unit,
    onWatchListTap: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabs = listOf("Movies", "Tv Shows")
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val scope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeTopBar(
                title = stringResource(R.string.discover),
                openSearch = {
                    onSearchTap()
                },
                openAccount = {
                    onAccountTap()
                },
                scrollBehavior = scrollBehavior,
                openWatchlist = {
                    onWatchListTap()
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            PrimaryTabRow(
                selectedTabIndex = pagerState.currentPage,
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                    )
                }
            }
            HorizontalPager(state = pagerState) { index ->
                when (index) {
                    0 ->
                        DiscoverMovieScreen(
                            onNavigateToMovie = onNavigateToMovie,
                        )
                    1 ->
                        DiscoverTvScreen(
                            onNavigateToTvShow = onNavigateToShow,
                        )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopBar(
    title: String,
    openSearch: () -> Unit,
    openAccount: () -> Unit,
    openWatchlist: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = {
            Text(text = title)
        },
        actions = {
            IconButton(onClick = {
                openSearch()
            }) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = stringResource(id = R.string.search_icon_text),
                )
            }
            IconButton(onClick = {
                openWatchlist()
            }) {
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = stringResource(R.string.watch_list_icon),
                )
            }
            IconButton(onClick = {
                openAccount()
            }) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = stringResource(id = R.string.login_icon_text),
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun HomeToolbarPreview() {
    HomeTopBar(
        title = "title",
        openSearch = {},
        openAccount = {},
        openWatchlist = {},
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    )
}
