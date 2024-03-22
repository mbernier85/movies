package im.bernier.movies.feature.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import im.bernier.movies.R
import im.bernier.movies.util.setTitle

@Composable
fun LoginRoute(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel(),
    onTitleChanged: (String) -> Unit,
) {
    LoginScreen(
        navController = navController,
        loginViewModel = loginViewModel,
        onTitleChanged = onTitleChanged
    )
}

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel,
    onTitleChanged: (String) -> Unit,
) {
    setTitle(stringId = R.string.login_title, onTitleChanged = onTitleChanged)
    val username = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val uiState = loginViewModel.uiState
    if (uiState.value.success) {
        LaunchedEffect(uiState) {
            navController.popBackStack()
        }
    }
    LoginScreenContent(onSend = {
        loginViewModel.login(username.value, password.value)
    }, username = username, password = password)
}

@Composable
fun LoginScreenContent(
    onSend: () -> Unit,
    username: MutableState<String>,
    password: MutableState<String>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            OutlinedTextField(
                value = username.value,
                label = {
                    Text(
                        text = stringResource(id = R.string.username)
                    )
                },
                onValueChange = {
                    username.value = it
                }
            )
            OutlinedTextField(
                value = password.value,
                label = {
                    Text(
                        text = stringResource(id = R.string.password)
                    )
                },
                onValueChange = {
                    password.value = it
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    onSend.invoke()
                })
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
    val username = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    LoginScreenContent(onSend = { }, username = username, password = password)
}