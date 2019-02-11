package im.bernier.movies.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

class MoviesDataSourceFactory: DataSource.Factory<Int, Movie>() {

    val sourceLiveData = MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val dataSource = MoviesDataSource()
        sourceLiveData.postValue(dataSource)
        return dataSource
    }
}