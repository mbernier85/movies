package im.bernier.movies.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

class MoviesDataSourceFactory(private val errors: MutableLiveData<Throwable>): DataSource.Factory<Int, Movie>() {

    val sourceLiveData = MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val dataSource = MoviesDataSource(errors)
        sourceLiveData.postValue(dataSource)
        return dataSource
    }
}