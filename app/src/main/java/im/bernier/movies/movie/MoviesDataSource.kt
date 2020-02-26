package im.bernier.movies.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import im.bernier.movies.datasource.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class MoviesDataSource(val errors: MutableLiveData<Throwable>): PageKeyedDataSource<Int, Movie>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        Repository.api.discover(1).enqueue(object: Callback<Page<Movie>?> {
            override fun onFailure(call: Call<Page<Movie>?>, t: Throwable) {
                errors.postValue(t)
            }

            override fun onResponse(call: Call<Page<Movie>?>, response: Response<Page<Movie>?>) {
                val page = response.body()
                if (response.isSuccessful && page != null) {
                    addGenre(page.results)
                    callback.onResult(page.results, page.page - 1, page.page + 1)
                } else {
                    errors.postValue(HttpException(response))
                }
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        Repository.api.discover(params.key).enqueue(object: Callback<Page<Movie>?> {
            override fun onFailure(call: Call<Page<Movie>?>, t: Throwable) {
                errors.postValue(t)
            }

            override fun onResponse(call: Call<Page<Movie>?>, response: Response<Page<Movie>?>) {
                val page = response.body()
                if (response.isSuccessful && page != null) {
                    addGenre(page.results)
                    callback.onResult(page.results, page.page + 1)
                } else {
                    errors.postValue(HttpException(response))
                }
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        // We only append data
    }

    private fun addGenre(movies: List<Movie>) {
        Thread {
            movies.forEach {
                val genres =
                    Repository.db.genreDao().loadAllByIds(it.genre_ids.toIntArray()).joinToString { genre -> genre.name }
                it.genreString = genres
            }
        }.start()
    }
}