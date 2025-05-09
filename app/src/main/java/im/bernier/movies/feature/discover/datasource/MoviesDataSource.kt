package im.bernier.movies.feature.discover.datasource

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import im.bernier.movies.datasource.Repository
import im.bernier.movies.feature.movie.Movie
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MoviesDataSource
    @Inject
    constructor(
        private val repository: Repository,
    ) : RxPagingSource<Int, Movie>() {
        override fun getRefreshKey(state: PagingState<Int, Movie>): Int? = getRefreshKeyAny(state)

        override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Movie>> =
            repository.api
                .discover(params.key ?: 1)
                .subscribeOn(Schedulers.io())
                .map {
                    it.results.forEach { movie ->
                        movie.genreString =
                            repository.db
                                .genreDao()
                                .loadAllByIds(movie.genre_ids.toIntArray())
                                .joinToString { genre -> genre.name }
                    }
                    it
                }.map {
                    val result: LoadResult<Int, Movie> =
                        LoadResult.Page(
                            data = it.results,
                            prevKey = null,
                            nextKey = it.page + 1,
                        )
                    result
                }.onErrorReturn { LoadResult.Error(it) }
    }
