package im.bernier.movies.feature.tv

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import im.bernier.movies.R
import im.bernier.movies.util.imageUrl
import im.bernier.movies.util.setTitle

const val tvShowIdArg = "tvShowId"

fun NavGraphBuilder.tvShowScreen(onTitleChanged: (String) -> Unit) {
    composable("tvShow/{$tvShowIdArg}") { _ ->
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TvShowView(
    tvShow: TV
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row {
            GlideImage(
                modifier = Modifier.size(120.dp, 180.dp),
                model = tvShow.poster_path.imageUrl(),
                contentDescription = stringResource(id = R.string.tv_show_poster)
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    text = tvShow.name
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = tvShow.overview
                )
            }
        }
    }
}

@Composable
@Preview
fun TvShowScreenPreview() {
    TvShowView(
        tvShow = TV(
            name = "name",
        )
    )
}
