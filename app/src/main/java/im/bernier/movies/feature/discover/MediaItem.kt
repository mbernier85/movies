package im.bernier.movies.feature.discover

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import im.bernier.movies.util.imageUrl


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MediaItem(
    item: MediaUiStateItem,
    onNavigateToMedia: (Long) -> Unit,
    onAddToWatchList: (Long, String) -> Unit
) {
    Card(
        modifier = Modifier.padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .clickable {
                    onNavigateToMedia(item.id)
                }
        ) {
            Row(
                modifier = Modifier
            ) {
                Box(
                    modifier = Modifier
                        .width(140.dp)
                        .height(200.dp)
                        .padding(horizontal = 8.dp)
                ) {
                    GlideImage(
                        model = item.posterPath.imageUrl(),
                        contentDescription = "Movie poster",
                        modifier = Modifier.clip(ShapeDefaults.Small)
                    )
                }

                Column {
                    Text(
                        text = item.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = item.genreString,
                        modifier = Modifier.padding(vertical = 8.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Clip,
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = item.overview,
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            HorizontalDivider()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                IconButton(onClick = {
                    onAddToWatchList(item.id, item.mediaType)
                }) {
                    Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = "Favorite")
                }
            }
        }
    }
}

@Composable
@Preview
fun MediaItemPreview() {
    MediaItem(
        item = MediaUiStateItem(
            id = 1,
            title = "Title",
            overview = "Overview",
            posterPath = "https://image.tmdb.org/t/p/w500/8bRIfPGDk2n3k3YGuI8q0d3i5xM.jpg",
            genreString = "Action",
            watchlist = false,
            mediaType = "movie"
        ),
        onNavigateToMedia = { }
    ) { _, _ -> }
}

data class MediaUiStateItem(
    val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String,
    val genreString: String = "",
    val watchlist: Boolean = false,
    val mediaType: String
)