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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import im.bernier.movies.feature.cast.Cast
import im.bernier.movies.feature.credits.Credits
import im.bernier.movies.util.imageUrl

@Composable
fun MovieScreen(
    viewModel: MovieViewModel,
    onNavigateToCast: ((Long) -> Unit),
    onTitleChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val movie = viewModel.movieState
    LaunchedEffect(key1 = movie) {
        movie.let {
            onTitleChange.invoke(it.title)
        }
    }
    MovieScreen(
        movie = movie,
        onNavigateToCast = onNavigateToCast,
        modifier = modifier,
    )
}

@Composable
private fun MovieScreen(
    movie: Movie,
    modifier: Modifier = Modifier,
    onNavigateToCast: ((Long) -> Unit) = {},
) {
    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn {
            item {
                Column {
                    AsyncImage(
                        model = movie.poster_path.imageUrl(),
                        contentDescription = movie.title,
                        modifier =
                            Modifier
                                .size(140.dp, 240.dp)
                                .padding(8.dp),
                    )
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier =
                            Modifier
                                .padding(8.dp),
                    )
                    Text(
                        text = movie.genreString,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier =
                            Modifier
                                .padding(8.dp),
                    )
                    Text(
                        text = movie.overview,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier =
                            Modifier
                                .padding(8.dp),
                    )
                    HorizontalDivider()
                }
            }
            movie.credits.cast.let {
                items(items = it) { cast ->
                    CastItem(person = cast, onNavigateToCast)
                }
            }
        }
    }
}

@Composable
private fun CastItem(
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

@Preview(showBackground = true)
@Composable
private fun MoviePreview() {
    MovieScreen(
        movie =
            Movie(
                title = "The Matrix",
                overview =
                    "A computer hacker learns from mysterious rebels about the true " +
                        "nature of his reality and his role in the war against its controllers.",
                genreString = "Action, Sci-Fi",
                credits =
                    Credits(
                        cast =
                            listOf(
                                Cast(
                                    name = "Keanu Reeves",
                                    character = "Neo",
                                    id = 0L,
                                    credit_id = "",
                                ),
                            ),
                    ),
            ),
    )
}
