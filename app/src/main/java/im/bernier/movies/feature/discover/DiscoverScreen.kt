package im.bernier.movies.feature.discover

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import im.bernier.movies.R
import im.bernier.movies.util.setTitle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(
    onTitleChanged: (String) -> Unit,
    onNavigateToMovie: (Long) -> Unit,
    onNavigateToShow: (Long) -> Unit
) {
    setTitle(stringId = R.string.discover) {
        onTitleChanged.invoke(it)
    }
    val tabs = listOf("Movies", "Tv Shows")
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val scope = rememberCoroutineScope()
    Column {
        PrimaryTabRow(
            selectedTabIndex = pagerState.currentPage,
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                )
            }
        }
        HorizontalPager(state = pagerState) { index ->
            when (index) {
                0 ->
                    DiscoverMovieScreen(
                        onNavigateToMovie = onNavigateToMovie,
                    )
                1 -> DiscoverTvScreen(
                    onNavigateToTvShow = onNavigateToShow
                )
            }
        }
    }
}
