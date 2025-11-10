package im.bernier.movies.feature.cast

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rxjava3.subscribeAsState
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
    onTitleChanged: (String) -> Unit,
) {
    val person by viewModel.person.subscribeAsState(initial = null)
    val tvShowCredits: List<Cast> = person?.tv_credits?.cast ?: listOf()
    val movieCredits: List<Cast> = person?.movie_credits?.cast ?: listOf()
    val credits: List<Cast> = tvShowCredits + movieCredits
    LaunchedEffect(person) {
        person?.let {
            onTitleChanged.invoke(it.name)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        person?.let {
            CastComponent(person = it)
        }
        CreditsList(credits = credits)
    }
}

@Composable
fun CastComponent(person: Person) {
    Column {
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
fun CreditsList(credits: List<Cast>) {
}

@Composable
@Preview
fun CastScreenPreview() {
    AppTheme {
        Surface {
            CastComponent(
                Person(
                    id = 1,
                    name = "Keanu Reeves",
                ),
            )
        }
    }
}
