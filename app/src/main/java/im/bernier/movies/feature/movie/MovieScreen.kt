package im.bernier.movies.feature.movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import im.bernier.movies.feature.cast.Cast
import im.bernier.movies.util.imageUrl

@Composable
fun MovieScreen(
    viewModel: MovieViewModel,
    onNavigateToCast: ((Long) -> Unit),
    onTitleChanged: (String) -> Unit,
) {
    val movie by viewModel.movie.subscribeAsState(initial = null)
    LaunchedEffect(key1 = movie) {
        movie?.let {
            onTitleChanged.invoke(it.title)
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            item {
                Column {
                    AsyncImage(
                        model = movie?.poster_path?.imageUrl(),
                        contentDescription = movie?.title,
                        modifier =
                            Modifier
                                .size(140.dp, 240.dp)
                                .padding(8.dp),
                    )
                    Text(
                        text = movie?.title ?: "",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier =
                            Modifier
                                .padding(8.dp),
                    )
                    Text(
                        text = movie?.genreString ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier =
                            Modifier
                                .padding(8.dp),
                    )
                    Text(
                        text = movie?.overview ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier =
                            Modifier
                                .padding(8.dp),
                    )
                    HorizontalDivider()
                }
            }
            movie?.credits?.cast?.let {
                items(items = it) { cast ->
                    CastItem(person = cast, onNavigateToCast)
                }
            }
        }
    }
}

@Composable
fun CastItem(
    person: Cast,
    onNavigateToCast: ((Long) -> Unit),
) {
    Row(
        modifier =
            Modifier
                .clickable {
                    onNavigateToCast(person.id)
                }.fillMaxWidth()
                .padding(8.dp),
    ) {
        AsyncImage(
            model = person.profile_path?.imageUrl(),
            contentDescription = person.name,
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .size(64.dp)
                    .clip(CircleShape),
        )
        Text(
            text = person.name,
            style = MaterialTheme.typography.bodyMedium,
            modifier =
                Modifier
                    .padding(8.dp),
        )
        Text(
            text = person.character,
            style = MaterialTheme.typography.bodyMedium,
            modifier =
                Modifier
                    .padding(8.dp),
        )
    }
}
