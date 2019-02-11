package im.bernier.movies.movie

import androidx.paging.PageKeyedDataSource
import im.bernier.movies.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MoviesDataSource: PageKeyedDataSource<Int, Movie>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        Repository.api.discover(1).enqueue(object: Callback<Page?> {
            override fun onFailure(call: Call<Page?>, t: Throwable) {
                Timber.e(t)
            }

            override fun onResponse(call: Call<Page?>, response: Response<Page?>) {
                val page = response.body()
                if (response.isSuccessful && page != null) {
                    addGenre(page.results)
                    callback.onResult(page.results, page.page - 1, page.page + 1)
                } else {
                    Timber.e(response.errorBody().toString())
                }
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        Repository.api.discover(params.key).enqueue(object: Callback<Page?> {
            override fun onFailure(call: Call<Page?>, t: Throwable) {
                Timber.e(t)
            }

            override fun onResponse(call: Call<Page?>, response: Response<Page?>) {
                val page = response.body()
                if (response.isSuccessful && page != null) {
                    addGenre(page.results)
                    callback.onResult(page.results, page.page + 1)
                } else {
                    Timber.e(response.errorBody().toString())
                }
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        Repository.api.discover(params.key).enqueue(object: Callback<Page?> {
            override fun onFailure(call: Call<Page?>, t: Throwable) {
                Timber.e(t)
            }

            override fun onResponse(call: Call<Page?>, response: Response<Page?>) {
                val page = response.body()
                if (response.isSuccessful && page != null) {
                    addGenre(page.results)
                    callback.onResult(page.results, page.page - 1)
                } else {
                    Timber.e(response.errorBody().toString())
                }
            }
        })
    }

    private fun addGenre(movies: List<Movie>) {
        Thread {
            movies.forEach {
                val genres =
                    Repository.db.genreDao().loadAllByIds(it.genre_ids.toIntArray()).joinToString { genre -> genre.name }
                it.genres = genres
            }
        }.start()
    }
}