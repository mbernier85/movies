package im.bernier.movies.feature.account

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object AccountRoute

fun NavController.navigateToAccount() = navigate(AccountRoute)

fun NavGraphBuilder.accountScreen(onTitleChanged: (String) -> Unit) {
    composable<AccountRoute> {
        AccountRoute(onTitleChanged = onTitleChanged)
    }
}
