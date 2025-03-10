package im.bernier.movies.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import im.bernier.movies.MoviesNavHost
import im.bernier.movies.R
import im.bernier.movies.feature.account.navigateToAccount
import im.bernier.movies.feature.authentication.navigateToLogin
import im.bernier.movies.navigateToSearch
import im.bernier.movies.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val openAccount = homeViewModel.uiState.value.openAccount
    val openLogin = homeViewModel.uiState.value.openLogin
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var title by remember {
        mutableStateOf("")
    }

    if (openAccount) {
        LaunchedEffect(homeViewModel.uiState) {
            navController.navigateToAccount()
            homeViewModel.uiState.value =
                homeViewModel.uiState.value.copy(openAccount = false)
        }
    }
    if (openLogin) {
        LaunchedEffect(homeViewModel.uiState) {
            navController.navigateToLogin()
            homeViewModel.uiState.value =
                homeViewModel.uiState.value.copy(openLogin = false)
        }
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
                        navController = navController,
                        openAccount = {
                            homeViewModel.openAccount()
                        },
                        scrollBehavior = scrollBehavior,
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
    navController: NavController,
    openAccount: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(text = title)
        },
        actions = {
            IconButton(onClick = {
                navController.navigateToSearch()
            }) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = stringResource(id = R.string.search_icon_text),
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

@Composable
@Preview
fun HomePreview() {
    HomeScreen()
}
