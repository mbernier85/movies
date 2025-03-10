package im.bernier.movies.util

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource

@SuppressLint("ComposableNaming")
@Composable
inline fun setTitle(
    stringId: Int,
    crossinline onTitleChanged: (String) -> Unit,
) {
    val titleValue = stringResource(id = stringId)
    val title by remember {
        mutableStateOf(titleValue)
    }
    LaunchedEffect(title) {
        onTitleChanged.invoke(title)
    }
}
