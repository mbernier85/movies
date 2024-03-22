package im.bernier.movies.feature.search

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import im.bernier.movies.R
import im.bernier.movies.util.imageUrl
import im.bernier.movies.util.setTitle
import timber.log.Timber

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onNavigateToMovie: ((Long) -> Unit),
    onNavigateToCast: ((Long) -> Unit),
    onTitleChanged: (String) -> Unit,
    onNavigateToTvShow: (Long) -> Unit
) {
    val searchResult by viewModel.searchResults.observeAsState(listOf())
    SearchContent(
        onNavigateToMovie,
        onNavigateToCast,
        onTitleChanged,
        onNavigateToTvShow,
        viewModel::submit,
        searchResult
    )
}

@Composable
fun SearchContent(
    onNavigateToMovie: ((Long) -> Unit),
    onNavigateToCast: ((Long) -> Unit),
    onTitleChanged: (String) -> Unit,
    onNavigateToTvShow: (Long) -> Unit,
    onSubmit: (String) -> Unit,
    searchResult: List<SearchResultItem>
) {
    setTitle(stringId = R.string.search_title, onTitleChanged = onTitleChanged)
    var text by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { text = it },
            label = {
                Text(text = "Search")
            },
            keyboardActions = KeyboardActions(onSearch = {
                onSubmit(text)
            }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            placeholder = { Text(text = "The matrix") },
            trailingIcon = {
                Icon(
                    Icons.Filled.Clear,
                    contentDescription = "Clear icon",
                    modifier = Modifier.clickable { text = "" })
            },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "search icon") },
            singleLine = true
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(
                items = searchResult,
            ) { searchResult ->
                SearchResultItem(
                    searchResult,
                    onNavigateToMovie,
                    onNavigateToCast,
                    onNavigateToTvShow
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SearchResultItem(
    searchResult: SearchResultItem,
    onNavigateToMovie: ((Long) -> Unit),
    onNavigateToCast: ((Long) -> Unit),
    onNavigateToTvShow: ((Long) -> Unit)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                when (searchResult.media_type) {
                    "movie" -> onNavigateToMovie(searchResult.id)
                    "tv" -> onNavigateToTvShow(searchResult.id)
                    "person" -> onNavigateToCast(searchResult.id)
                    else -> Timber.e("Unknown media type: ${searchResult.media_type}")
                }
            },
    ) {
        Box(
            modifier = Modifier
                .width(140.dp)
                .height(240.dp)
                .padding(horizontal = 8.dp)
        ) {
            val imagePath =
                searchResult.profile_path?.imageUrl() ?: searchResult.poster_path?.imageUrl() ?: ""
            GlideImage(
                model = imagePath,
                contentDescription = "Movie poster or profile picture"
            )
        }

        Column {
            Text(
                text = searchResult.title ?: searchResult.name ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
@Preview
fun SearchScreenPreview() {
    SearchContent(
        onNavigateToMovie = {},
        onNavigateToCast = {},
        onTitleChanged = {},
        onNavigateToTvShow = {},
        onSubmit = {},
        searchResult = listOf(
            SearchResultItem(
                id = 1,
                media_type = "movie",
                title = "The Matrix",
                poster_path = "/lZpWprJqbIFpEV5uoHfoK0KCnTW.jpg"
            ),
            SearchResultItem(
                id = 2,
                media_type = "tv",
                name = "The Matrix",
                poster_path = "/lZpWprJqbIFpEV5uoHfoK0KCnTW.jpg"
            ),
            SearchResultItem(
                id = 3,
                media_type = "person",
                name = "Keanu Reeves",
                profile_path = "/lZpWprJqbIFpEV5uoHfoK0KCnTW.jpg"
            )
        )
    )
}