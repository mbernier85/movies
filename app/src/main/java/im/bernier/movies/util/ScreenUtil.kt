package im.bernier.movies.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource

@Composable
inline fun SetTitle(
    stringId: Int,
    crossinline onTitleChange: (String) -> Unit,
) {
    val titleValue = stringResource(id = stringId)
    val title by remember {
        mutableStateOf(titleValue)
    }
    LaunchedEffect(title) {
        onTitleChange.invoke(title)
    }
}
