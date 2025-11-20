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
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.autofill.FillableData
import androidx.compose.ui.autofill.createFromText
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.fillableData
import androidx.compose.ui.semantics.onFillData
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

@OptIn(ExperimentalMaterial3Api::class)
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
            val keyboard = LocalSoftwareKeyboardController.current
            OutlinedTextField(
                modifier = Modifier.semantics {
                    contentType = ContentType.Username + ContentType.EmailAddress
                    FillableData.createFromText(username.text)?.let { fillableData = it }
                    onFillData {
                        keyboard?.hide()
                        it.textValue?.let { textValue ->
                            username.edit { replace(0, length, textValue) }
                        }
                        true
                    }
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

            var passwordHidden by rememberSaveable { mutableStateOf(true) }
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
                },
                textObfuscationMode =
                    if (passwordHidden) TextObfuscationMode.RevealLastTyped
                    else TextObfuscationMode.Visible,
                trailingIcon = {
                    // Provide localized description for accessibility services
                    val description = if (passwordHidden) stringResource(R.string.show_password) else stringResource(
                        R.string.hide_password
                    )
                    TooltipBox(
                        positionProvider =
                            TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Above),
                        tooltip = { PlainTooltip { Text(description) } },
                        state = rememberTooltipState(),
                    ) {
                        IconButton(onClick = { passwordHidden = !passwordHidden }) {
                            val visibilityIcon =
                                if (passwordHidden) painterResource(R.drawable.outline_visibility_24) else painterResource(
                                    R.drawable.outline_visibility_off_24
                                )
                            Icon(painter = visibilityIcon, contentDescription = description)
                        }
                    }
                },
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
