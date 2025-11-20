package im.bernier.movies.feature.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import im.bernier.movies.R
import im.bernier.movies.util.setTitle

@Composable
fun LoginRoute(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onTitleChanged: (String) -> Unit,
    onLoginSuccess: () -> Unit,
) {
    LoginScreen(
        loginViewModel = loginViewModel,
        onTitleChanged = onTitleChanged,
        onLoginSuccess = onLoginSuccess
    )
}

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    onTitleChanged: (String) -> Unit,
    onLoginSuccess: () -> Unit,
) {
    setTitle(stringId = R.string.login_title, onTitleChanged = onTitleChanged)
    val username = rememberTextFieldState(initialText = "")
    val password = rememberTextFieldState(initialText = "")
    val uiState = loginViewModel.uiState
    LaunchedEffect(uiState) {
        if (uiState.success) {
            onLoginSuccess()
        }
    }
    LoginScreenContent(onSend = {
        loginViewModel.login(username.text.toString(), password.text.toString())
    }, username = username, password = password)
}

@Composable
fun LoginScreenContent(
    onSend: () -> Unit,
    username: TextFieldState,
    password: TextFieldState,
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.CenterStart,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            OutlinedTextField(
                modifier = Modifier.semantics {
                    contentType = ContentType.Username + ContentType.EmailAddress
                },
                state = username,
                label = {
                    Text(
                        text = stringResource(id = R.string.username),
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
            OutlinedSecureTextField(
                modifier = Modifier.semantics { contentType = ContentType.Password },
                state = password,
                label = {
                    Text(
                        text = stringResource(id = R.string.password),
                    )
                },
                keyboardOptions =
                    KeyboardOptions(
                        autoCorrectEnabled = false,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                    ),
                onKeyboardAction = { defaultAction ->
                    onSend()
                    defaultAction()
                }
            )
            Button(onClick = onSend) {
                Text(text = stringResource(id = R.string.login))
            }
        }
    }
}

@Composable
@Preview
fun LoginPreview() {
    LoginScreenContent(
        onSend = { },
        username = rememberTextFieldState(),
        password = rememberTextFieldState()
    )
}
