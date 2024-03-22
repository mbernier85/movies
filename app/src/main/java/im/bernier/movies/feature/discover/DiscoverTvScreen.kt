package im.bernier.movies.feature.discover

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import im.bernier.movies.feature.tv.TV
import im.bernier.movies.feature.tv.navigateToTvShow
import kotlinx.coroutines.flow.Flow

@Composable
fun DiscoverTvScreen(
    viewModel: DiscoverTvViewModel = hiltViewModel(),
    navController: NavController
) {
    val pager: Flow<PagingData<TV>> = viewModel.pager
    TvList(
        pager = pager,
        onNavigateToTvShow = {
            navController.navigateToTvShow(it)
        },
        onAddToWatchList = viewModel::onAddToWatchList,
    )
}

@Composable
fun TvList(
    pager: Flow<PagingData<TV>>,
    onNavigateToTvShow: (Long) -> Unit,
    onAddToWatchList: (Long, String) -> Unit,
) {
    val lazyPagingItems = pager.collectAsLazyPagingItems()
    LazyColumn(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        items(
            count = lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { it.id },
        ) { index ->
            val tv = lazyPagingItems[index]
            if (tv != null) {
                MediaItem(
                    item = tv.toMediaUiStateItem(),
                    onNavigateToMedia = {
                        onNavigateToTvShow(it)
                    },
                    onAddToWatchList = onAddToWatchList
                )
            }
        }
    }
}
