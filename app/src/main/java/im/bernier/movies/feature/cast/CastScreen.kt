package im.bernier.movies.feature.cast

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import im.bernier.movies.theme.AppTheme
import im.bernier.movies.util.imageUrl

@Composable
fun CastScreen(
    viewModel: CastViewModel,
    onTitleChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val person = viewModel.person

    LaunchedEffect(person) {
        onTitleChange.invoke(person.name)
    }
    CastScreenContent(person = person, modifier = modifier)
}

@Composable
private fun CastScreenContent(
    person: Person,
    modifier: Modifier = Modifier,
) {
    val tvShowCredits: List<Cast> = person.tv_credits?.cast ?: listOf()
    val movieCredits: List<Cast> = person.movie_credits?.cast ?: listOf()
    val credits: List<Cast> = tvShowCredits + movieCredits
    LazyColumn(
        modifier =
            modifier
                .fillMaxSize(),
    ) {
        item {
            CastComponent(person = person)
        }
        items(items = credits) { credit ->
            Credit(credit)
        }
    }
}

@Composable
private fun CastComponent(
    person: Person,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        AsyncImage(
            model = person.profile_path?.imageUrl(),
            contentDescription = person.name,
            modifier =
                Modifier
                    .size(120.dp, 180.dp)
                    .clip(CircleShape)
                    .padding(16.dp),
        )
        Text(
            text = person.name,
            style = MaterialTheme.typography.headlineMedium,
            modifier =
                Modifier
                    .padding(16.dp),
        )
        Text(
            text = person.biography,
            style = MaterialTheme.typography.bodyMedium,
            modifier =
                Modifier
                    .padding(16.dp),
        )
    }
}

@Composable
private fun Credit(credit: Cast) {
    Column(
        modifier =
            Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
    ) {
        Row {
            AsyncImage(
                modifier =
                    Modifier
                        .size(64.dp)
                        .padding(end = 8.dp),
                model = credit.poster_path?.imageUrl(),
                contentDescription = "profile image",
            )
            val title = credit.name.ifEmpty { credit.title }
            Column {
                Text(
                    text = "Character : ${credit.character}",
                )
                Text(
                    text = "Title : $title",
                )
            }
        }
    }
}

@Composable
@Preview
private fun CastScreenPreview() {
    AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            CastScreenContent(
                Person(
                    id = 1,
                    name = "Keanu Reeves",
                    movie_credits =
                        Casts(
                            listOf(
                                Cast(
                                    character = "Neo",
                                    title = "The matrix",
                                    credit_id = "credit_id",
                                    id = 1L,
                                ),
                            ),
                        ),
                ),
            )
        }
    }
}
