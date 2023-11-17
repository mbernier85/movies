package im.bernier.movies

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import im.bernier.movies.movie.MovieListScreen

private const val movieIdArg = "movieId"

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
                onNavigateToMovie = { navController.navigate("movie/$it")},
                onNavigateToSearch = {},
            )
        }
        composable("movie/{$movieIdArg}") {

        }
    }
}