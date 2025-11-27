package im.bernier.movies.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import im.bernier.movies.R
import im.bernier.movies.R.drawable.outline_bookmark_added_24
import im.bernier.movies.util.imageUrl

@Composable
fun MediaItem(
    item: MediaUiStateItem,
    onNavigateToMedia: (Long) -> Unit,
    modifier: Modifier = Modifier,
    onAddToWatchList: ((Long, String) -> Unit)? = null,
) {
    ElevatedCard(
        modifier = modifier.padding(4.dp),
        colors =
            CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
    ) {
        Column(
            modifier =
                Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .clickable {
                        onNavigateToMedia(item.id)
                    },
        ) {
            Row(
                modifier = Modifier,
            ) {
                Box(
                    modifier =
                        Modifier
                            .width(140.dp)
                            .height(200.dp)
                            .padding(horizontal = 8.dp),
                ) {
                    AsyncImage(
                        model = item.posterPath?.imageUrl(),
                        contentDescription = "Movie poster",
                        modifier = Modifier.clip(ShapeDefaults.Small),
                    )
                }

                Column {
                    Text(
                        text = item.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Text(
                        text = item.genreString,
                        modifier = Modifier.padding(vertical = 8.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Clip,
                        style = MaterialTheme.typography.labelSmall,
                    )
                    Text(
                        text = item.overview,
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outline,
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                onAddToWatchList?.let {
                    IconButton(onClick = {
                        it(item.id, item.mediaType)
                    }) {
                        val icon =
                            if (item.watchlist) {
                                outline_bookmark_added_24
                            } else {
                                R.drawable.outline_bookmark_add_24
                            }
                        Icon(
                            painter = painterResource(icon),
                            contentDescription = "Favorite",
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun MediaItemPreview() {
    MediaItem(
        item =
            MediaUiStateItem(
                id = 1,
                title = "Title",
                overview = "Overview",
                posterPath = "https://image.tmdb.org/t/p/w500/8bRIfPGDk2n3k3YGuI8q0d3i5xM.jpg",
                genreString = "Action",
                watchlist = false,
                mediaType = "movie",
            ),
        onNavigateToMedia = { },
    ) { _, _ -> }
}

data class MediaUiStateItem(
    val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val genreString: String = "",
    val watchlist: Boolean = false,
    val mediaType: String,
)
