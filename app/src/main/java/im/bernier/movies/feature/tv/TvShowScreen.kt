package im.bernier.movies.feature.tv

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import im.bernier.movies.R
import im.bernier.movies.util.setTitle

const val tvShowIdArg = "tvShowId"

fun NavGraphBuilder.tvShowScreen(onTitleChanged: (String) -> Unit) {
    composable("tvShow/{$tvShowIdArg}") { backStackEntry ->
        TvShowScreen(
            onTitleChanged = onTitleChanged,
        )
    }
}

fun NavController.navigateToTvShow(id: Long) {
    this.navigate("tvShow/$id")
}

@Composable
fun TvShowScreen(
    viewModel: TvShowViewModel = hiltViewModel(),
    onTitleChanged: (String) -> Unit
) {
    val tvShow by viewModel.tvShow.subscribeAsState(initial = null)
    setTitle(stringId = R.string.tv_show) {
        onTitleChanged.invoke(it)
    }
    tvShow?.let {
        TvShowView(tvShow = it)
    }
}

@Composable
fun TvShowView(
    tvShow: TV
) {

}

@Composable
@Preview
fun TvShowScreenPreview() {
    TvShowView(tvShow = TV())
}
