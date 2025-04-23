package im.bernier.movies.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import im.bernier.movies.MoviesNavHost
import im.bernier.movies.R
import im.bernier.movies.feature.account.navigateToAccount
import im.bernier.movies.feature.authentication.navigateToLogin
import im.bernier.movies.feature.watchlist.navigateToWatchList
import im.bernier.movies.navigateToSearch
import im.bernier.movies.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var title by remember {
        mutableStateOf("")
    }

    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    HomeTopBar(
                        title = title,
                        openSearch = { navController.navigateToSearch() },
                        openAccount = {
                            if (homeViewModel.loggedIn) {
                                navController.navigateToAccount()
                            } else {
                                navController.navigateToLogin()
                            }
                        },
                        scrollBehavior = scrollBehavior,
                        openWatchlist = {
                            if (homeViewModel.loggedIn) {
                                navController.navigateToWatchList()
                            } else {
                                navController.navigateToLogin()
                            }
                        }
                    )
                },
            ) { paddingValues ->
                Column(
                    modifier = Modifier.padding(paddingValues),
                ) {
                    MoviesNavHost(
                        navController = navController,
                        onTitleChanged = {
                            title = it
                        },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    title: String,
    openSearch: () -> Unit,
    openAccount: () -> Unit,
    openWatchlist: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    TopAppBar(
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
                    contentDescription = stringResource(R.string.watch_list_icon)
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
fun HomeToolbarPreview() {
    HomeTopBar(
        title = "title",
        openSearch = {},
        openAccount = {},
        openWatchlist = {},
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    )
}
