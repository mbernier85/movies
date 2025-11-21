package im.bernier.movies.feature.discover.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import im.bernier.movies.datasource.Repository
import im.bernier.movies.feature.tv.TV
import javax.inject.Inject

class TVDataSource
    @Inject
    constructor(
        private val repository: Repository,
    ) : PagingSource<Int, TV>() {
        override fun getRefreshKey(state: PagingState<Int, TV>): Int? = getRefreshKeyAny(state)

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TV> =
            repository
                .discoverTV(params.key ?: 1)
                .run {
                    LoadResult.Page(
                        data = this.results,
                        prevKey = null,
                        nextKey = this.page + 1,
                    )
                }
    }
