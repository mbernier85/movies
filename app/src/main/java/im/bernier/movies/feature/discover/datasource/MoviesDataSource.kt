package im.bernier.movies.feature.discover.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import im.bernier.movies.datasource.Repository
import im.bernier.movies.feature.movie.Movie
import jakarta.inject.Inject

class MoviesDataSource
    @Inject
    constructor(
        private val repository: Repository,
    ) : PagingSource<Int, Movie>() {
        override fun getRefreshKey(state: PagingState<Int, Movie>): Int? = getRefreshKeyAny(state)

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> =
            repository
                .discover(params.key ?: 1)
                .run {
                    LoadResult.Page(
                        data = this.results,
                        prevKey = null,
                        nextKey = this.page + 1,
                    )
                }
    }
