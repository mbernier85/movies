package im.bernier.movies.feature.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import im.bernier.movies.theme.AppTheme

@Composable
fun AccountRoute(
    accountViewModel: AccountViewModel = hiltViewModel(),
    onTitleChanged: (String) -> Unit
) {
    AccountScreen(
        accountViewModel = accountViewModel,
        onTitleChanged = onTitleChanged
    )
}

@Composable
fun AccountScreen(
    accountViewModel: AccountViewModel,
    onTitleChanged: (String) -> Unit,
) {
    val uiState = accountViewModel.uiState.subscribeAsState(initial = UiState(""))
    LaunchedEffect(uiState) {
        onTitleChanged.invoke(uiState.value.name)
    }
    AccountContent(uiState.value)
}

@Composable
fun AccountContent(
    uiState: UiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = uiState.name)
    }
}

@Composable
@Preview
fun AccountPreview() {
    AppTheme {
        Surface {
            AccountContent(UiState(name = "Michael Bernier"))
        }
    }
}
