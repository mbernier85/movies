package im.bernier.movies.feature.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet
import im.bernier.movies.feature.authentication.LoginRoute
import im.bernier.movies.navigation.EntryProviderInstaller
import im.bernier.movies.navigation.Navigator
import im.bernier.movies.theme.AppTheme
import kotlinx.serialization.Serializable

@Serializable
data object AccountRoute : Navigator.RequiresLogin

@Module
@InstallIn(ActivityRetainedComponent::class)
object AccountModule {
    @IntoSet
    @Provides
    fun provideEntryProviderInstaller(navigator: Navigator): EntryProviderInstaller =
        {
            entry<AccountRoute> {
                AccountRoute(
                    onLogout = {
                        navigator.goBack()
                        navigator.goTo(LoginRoute)
                    },
                )
            }
        }
}

@Composable
fun AccountRoute(
    onLogout: () -> Unit,
    accountViewModel: AccountViewModel = hiltViewModel(),
) {
    AccountScreen(
        uiState = accountViewModel.uiState,
        onLogout = {
            accountViewModel.logout()
            onLogout()
        },
    )
}

@Composable
fun AccountScreen(
    uiState: UiState,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AccountContent(
        uiState = uiState,
        onLogout = onLogout,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AccountContent(
    uiState: UiState,
    modifier: Modifier = Modifier,
    onLogout: () -> Unit = {},
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        Text(text = uiState.name)
        Button(
            shapes = ButtonDefaults.shapes(),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp),
            onClick = onLogout,
        ) {
            Text(text = "Logout")
        }
    }
}

@Composable
@Preview
private fun AccountPreview() {
    AppTheme {
        Surface {
            AccountContent(
                UiState(name = "Michael Bernier"),
            )
        }
    }
}
