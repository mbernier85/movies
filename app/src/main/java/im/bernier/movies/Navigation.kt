package im.bernier.movies

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import im.bernier.movies.feature.cast.CastScreen
import im.bernier.movies.feature.movie.MovieListScreen
import im.bernier.movies.feature.movie.MovieScreen
import im.bernier.movies.feature.search.SearchScreen

const val movieIdArg = "movieId"
const val castIdArg = "personId"

@Composable
fun MoviesNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "movies"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("movies") {
            MovieListScreen(
                viewModel = hiltViewModel(),
                onNavigateToMovie = { navController.navigate("movie/$it") },
                onNavigateToSearch = { navController.navigate("search") },
            )
        }
        composable("movie/{$movieIdArg}") {
            MovieScreen(viewModel = hiltViewModel(), onNavigateToCast = {
                navController.navigate("cast/$it")
            })
        }
        composable("search") {
            SearchScreen(
                onNavigateToMovie = { navController.navigate("movie/$it") },
                onNavigateToCast = { navController.navigate("cast/$it") }
            )
        }
        composable("cast/{$castIdArg}") {
            CastScreen(viewModel = hiltViewModel())
        }
    }
}