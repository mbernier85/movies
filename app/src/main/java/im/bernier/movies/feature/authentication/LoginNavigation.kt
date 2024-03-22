package im.bernier.movies.feature.authentication

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val LOGIN_ROUTE = "login"

fun NavController.navigateToLogin() {
    navigate(route = LOGIN_ROUTE)
}

fun NavGraphBuilder.loginScreen(
    navController: NavController,
    onTitleChanged: (String) -> Unit
) {
    composable(route = LOGIN_ROUTE) {
        LoginRoute(navController, onTitleChanged = onTitleChanged)
    }
}