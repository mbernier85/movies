package im.bernier.movies.feature.discover

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import im.bernier.movies.R
import im.bernier.movies.util.setTitle
import kotlinx.coroutines.launch


@Composable
fun DiscoverRoute(
    onTitleChanged: (String) -> Unit,
    navController: NavController
) {
    DiscoverScreen(
        onTitleChanged = onTitleChanged,
        navController = navController
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(
    onTitleChanged: (String) -> Unit,
    navController: NavController
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
                    }
                )
            }
        }
        HorizontalPager(state = pagerState) { index ->
            when (index) {
                0 -> DiscoverMovieScreen(
                    navController = navController,
                )
                1 -> DiscoverTvScreen(
                    navController = navController,
                )
            }
        }

    }
}