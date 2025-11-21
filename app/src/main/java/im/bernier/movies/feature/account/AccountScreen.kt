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
    onTitleChange: (String) -> Unit,
    onLogout: () -> Unit,
    accountViewModel: AccountViewModel = hiltViewModel(),
) {
    AccountScreen(
        uiState = accountViewModel.uiState,
        onTitleChange = onTitleChange,
        onLogout = {
            accountViewModel.logout()
            onLogout()
        },
    )
}

@Composable
fun AccountScreen(
    uiState: UiState,
    onTitleChange: (String) -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(uiState) {
        onTitleChange.invoke(uiState.name)
    }
    AccountContent(
        uiState = uiState,
        onLogout = onLogout,
        modifier = modifier,
    )
}

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
