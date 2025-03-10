package im.bernier.movies.feature.authentication

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

fun NavController.navigateToLogin() {
    navigate(LoginRoute)
}

fun NavGraphBuilder.loginScreen(
    navController: NavController,
    onTitleChanged: (String) -> Unit,
) {
    composable<LoginRoute> {
        LoginRoute(navController, onTitleChanged = onTitleChanged)
    }
}
