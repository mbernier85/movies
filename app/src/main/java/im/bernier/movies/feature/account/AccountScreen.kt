package im.bernier.movies.feature.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import im.bernier.movies.theme.AppTheme

@Composable
fun AccountRoute(
    accountViewModel: AccountViewModel = hiltViewModel(),
    onTitleChanged: (String) -> Unit,
    onLogout: () -> Unit
) {
    AccountScreen(
        accountViewModel = accountViewModel,
        onTitleChanged = onTitleChanged,
        onLogout = onLogout
    )
}

@Composable
fun AccountScreen(
    accountViewModel: AccountViewModel,
    onTitleChanged: (String) -> Unit,
    onLogout: () -> Unit,
) {
    val uiState = accountViewModel.uiState
    LaunchedEffect(uiState) {
        onTitleChanged.invoke(uiState.name)
    }
    AccountContent(
        uiState,
        onLogout = {
            accountViewModel.logout()
            onLogout()
        }
    )
}

@Composable
fun AccountContent(
    uiState: UiState,
    onLogout: () -> Unit = {},
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        Text(text = uiState.name)
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp),
            onClick = onLogout
        ) {
            Text(text = "Logout")
        }
    }
}

@Composable
@Preview
fun AccountPreview() {
    AppTheme {
        Surface {
            AccountContent(
                UiState(name = "Michael Bernier")
            )
        }
    }
}
