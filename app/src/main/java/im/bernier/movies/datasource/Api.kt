package im.bernier.movies.datasource

import im.bernier.movies.credits.Credits
import im.bernier.movies.genre.Genres
import im.bernier.movies.movie.Movie
import im.bernier.movies.movie.Page
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("discover/movie")
    fun discover(@Query("page") page: Int): Call<Page>

    @GET("genre/movie/list")
    fun genres(): Call<Genres>

    @GET("movie/{movieId}?append_to_response=credits")
    fun getMovie(@Path("movieId") movieId: Long): Call<Movie>

    @GET("movie/{movieId}/credits")
    fun getMovieCredits(@Path("movieId") movieId: Long): Call<Credits>
}