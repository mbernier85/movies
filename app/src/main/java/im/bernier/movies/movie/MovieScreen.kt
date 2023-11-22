package im.bernier.movies.movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import im.bernier.movies.cast.Cast
import im.bernier.movies.util.imageUrl

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieScreen(viewModel: MovieViewModel) {
    val movie by viewModel.observableMovie().subscribeAsState(initial = null)
    Column {
        GlideImage(
            model = movie?.poster_path?.imageUrl(),
            contentDescription = movie?.title,
            modifier = Modifier
                .size(140.dp, 240.dp)
                .padding(8.dp)
        )
        Text(
            text = movie?.title ?: "",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(8.dp)
        )
        Text(
            text = movie?.genreString ?: "",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(8.dp)
        )
        Text(
            text = movie?.overview ?: "",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(8.dp)
        )
        Divider()
        if (movie?.credits?.cast?.isNotEmpty() == true) {
            LazyColumn {
                items(items = movie!!.credits.cast) {
                    CastItem(person = it)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CastItem(person: Cast) {
    Row(
        modifier = Modifier
            .clickable {
                // Do something when the item is clicked
            }
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        GlideImage(
            model = person.profile_path?.imageUrl(),
            contentDescription = person.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )
        Text(
            text = person.name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(8.dp)
        )
        Text(
            text = person.character,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}
