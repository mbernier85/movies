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
import kotlinx.serialization.Serializable

fun NavController.navigateToMovie(id: Long) {
    this.navigate(MovieRoute(id))
}

fun NavController.navigateToSearch() {
    this.navigate(SearchRoute)
}

fun NavController.navigateToCast(id: Long) {
    this.navigate(CastRoute(id))
}

@Serializable
data object DiscoverRoute

@Serializable
data class MovieRoute(
    val id: Long,
)

@Serializable
data class CastRoute(
    val id: Long,
)

@Serializable
data object SearchRoute

@Composable
fun MoviesNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Any = DiscoverRoute,
    onTitleChanged: (String) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
    ) {
        composable<DiscoverRoute> {
            DiscoverRoute(
                onTitleChanged = onTitleChanged,
                navController = navController,
            )
        }
        composable<MovieRoute> {
            MovieScreen(
                viewModel = hiltViewModel(),
                onTitleChanged = onTitleChanged,
                onNavigateToCast = {
                    navController.navigateToCast(it)
                },
            )
        }
        composable<SearchRoute> {
            SearchScreen(
                onNavigateToMovie = { navController.navigateToMovie(it) },
                onNavigateToCast = { navController.navigateToCast(it) },
                onNavigateToTvShow = { navController.navigateToTvShow(it) },
                onTitleChanged = onTitleChanged,
            )
        }
        composable<CastRoute> {
            CastScreen(
                viewModel = hiltViewModel(),
                onTitleChanged = onTitleChanged,
            )
        }
        loginScreen(navController, onTitleChanged)
        accountScreen(onTitleChanged)
        tvShowScreen(onTitleChanged)
    }
}
