package im.bernier.movies.feature.account

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val ACCOUNT_ROUTE = "account"

fun NavController.navigateToAccount() = navigate(ACCOUNT_ROUTE)

fun NavGraphBuilder.accountScreen(
    onTitleChanged: (String) -> Unit
) {
    composable(route = ACCOUNT_ROUTE) {
        AccountRoute(onTitleChanged = onTitleChanged)
    }
}