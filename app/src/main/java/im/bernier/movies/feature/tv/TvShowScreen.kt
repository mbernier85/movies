package im.bernier.movies.feature.tv

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import im.bernier.movies.R
import im.bernier.movies.util.imageUrl
import im.bernier.movies.util.setTitle

@Composable
fun TvShowScreen(
    viewModel: TvShowViewModel,
    onTitleChanged: (String) -> Unit,
) {
    val tvShow = viewModel.tvShow
    setTitle(stringId = R.string.tv_show) {
        onTitleChanged.invoke(it)
    }
    TvShowView(tvShow = tvShow)
}

@Composable
private fun TvShowView(tvShow: TV) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        Row {
            AsyncImage(
                modifier = Modifier.size(120.dp, 180.dp),
                model = tvShow.poster_path?.imageUrl(),
                contentDescription = stringResource(id = R.string.tv_show_poster),
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    text = tvShow.name,
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = tvShow.overview,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun TvShowScreenPreview() {
    TvShowView(
        tvShow =
            TV(
                name = "name",
            ),
    )
}
