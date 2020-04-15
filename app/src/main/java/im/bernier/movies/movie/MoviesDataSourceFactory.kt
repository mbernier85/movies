package im.bernier.movies.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import im.bernier.movies.datasource.Repository

class MoviesDataSourceFactory constructor(private val errors: MutableLiveData<Throwable>, private val repository: Repository): DataSource.Factory<Int, Movie>() {

    val sourceLiveData = MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val dataSource = MoviesDataSource(errors, repository)
        sourceLiveData.postValue(dataSource)
        return dataSource
    }
}