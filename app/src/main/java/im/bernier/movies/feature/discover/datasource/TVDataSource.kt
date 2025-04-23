package im.bernier.movies.feature.discover.datasource

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import im.bernier.movies.datasource.Repository
import im.bernier.movies.feature.tv.TV
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class TVDataSource
    @Inject
    constructor(
        private val repository: Repository,
    ) : RxPagingSource<Int, TV>() {
        override fun getRefreshKey(state: PagingState<Int, TV>): Int? = getRefreshKeyAny(state)

        override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, TV>> =
            repository.api
                .discoverTV(params.key ?: 1)
                .subscribeOn(Schedulers.io())
                .map {
                    it.results.forEach { tv ->
                        tv.genreString =
                            repository.db
                                .genreDao()
                                .loadAllByIds(tv.genre_ids.toIntArray())
                                .joinToString { genre -> genre.name }
                    }
                    it
                }.map {
                    val result: LoadResult<Int, TV> =
                        LoadResult.Page(
                            data = it.results,
                            prevKey = null,
                            nextKey = it.page + 1,
                        )
                    result
                }.onErrorReturn { LoadResult.Error(it) }
    }
