package im.bernier.movies

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import im.bernier.movies.feature.account.accountScreen
import im.bernier.movies.feature.authentication.loginScreen
import im.bernier.movies.feature.cast.CastScreen
import im.bernier.movies.feature.discover.DiscoverRoute
import im.bernier.movies.feature.movie.MovieScreen
import im.bernier.movies.feature.search.SearchScreen
import im.bernier.movies.feature.tv.navigateToTvShow
import im.bernier.movies.feature.tv.tvShowScreen

const val movieIdArg = "movieId"
const val castIdArg = "personId"

fun NavController.navigateToMovie(id: Long) {
    this.navigate("movie/$id")
}

fun NavController.navigateToSearch() {
    this.navigate("search")
}

fun NavController.navigateToCast(id: Long) {
    this.navigate("cast/$id")
}

const val DISCOVER_ROUTE = "discover"

@Composable
fun MoviesNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = DISCOVER_ROUTE,
    onTitleChanged: (String) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }
    ) {
        composable(DISCOVER_ROUTE) {
            DiscoverRoute(
                onTitleChanged = onTitleChanged,
                navController = navController
            )
        }
        composable("movie/{$movieIdArg}") {
            MovieScreen(
                viewModel = hiltViewModel(),
                onTitleChanged = onTitleChanged,
                onNavigateToCast = {
                    navController.navigateToCast(it)
                }
            )
        }
        composable("search") {
            SearchScreen(
                onNavigateToMovie = { navController.navigateToMovie(it) },
                onNavigateToCast = { navController.navigateToCast(it) },
                onNavigateToTvShow = { navController.navigateToTvShow(it) },
                onTitleChanged = onTitleChanged
            )
        }
        composable("cast/{$castIdArg}") {
            CastScreen(
                viewModel = hiltViewModel(),
                onTitleChanged = onTitleChanged
            )
        }
        loginScreen(navController, onTitleChanged)
        accountScreen(onTitleChanged)
        tvShowScreen(onTitleChanged)
    }
}