package im.bernier.movies.datasource

import im.bernier.movies.feature.cast.Person
import im.bernier.movies.feature.genre.Genres
import im.bernier.movies.feature.movie.Movie
import im.bernier.movies.feature.movie.Page
import im.bernier.movies.feature.search.SearchResultItem
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("discover/movie")
    fun discover(@Query("page") page: Int): Single<Page<Movie>>

    @GET("genre/movie/list")
    fun genres(): Call<Genres>

    @GET("movie/{movieId}?append_to_response=credits")
    fun getMovie(@Path("movieId") movieId: Long): Observable<Movie>

    @GET("search/multi")
    fun search(@Query("query") query: String): Call<Page<SearchResultItem>>

    @GET("person/{person_id}")
    fun getCastById(@Path("person_id") id: Long): Observable<Person>
}