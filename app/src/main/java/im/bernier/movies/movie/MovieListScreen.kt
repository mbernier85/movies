package im.bernier.movies.movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.Pager
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import im.bernier.movies.theme.AppTheme
import im.bernier.movies.util.imageUrl

@Composable
fun MovieListScreen(viewModel: MoviesViewModel = viewModel()) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.search() }) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MovieList(pager = viewModel.pager)
        }
    }
}

@Composable
fun MovieList(pager: Pager<Int, Movie>) {
    val lazyPagingItems = pager.flow.collectAsLazyPagingItems()
    LazyColumn(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        items(
            count = lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { it.id },
        ) { index ->
            val movie = lazyPagingItems[index]
            if (movie != null) {
                MovieItem(movie = movie)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(movie: Movie) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {

            },
    ) {
        Box(
            modifier = Modifier
                .width(140.dp)
                .height(240.dp)
                .padding(horizontal = 8.dp)
        ) {
            GlideImage(
                model = movie.poster_path.imageUrl(),
                contentDescription = "Movie poster"
            )
        }

        Column() {
            Text(
                text = movie.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = movie.genreString,
                modifier = Modifier.padding(vertical = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = movie.overview,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
fun MovieListScreenPreview() {
    AppTheme {
        MovieItem(
            Movie(
                title = "The matrix",
                overview = "",
                genreString = "Action, Thriller"
            )
        )
    }
}