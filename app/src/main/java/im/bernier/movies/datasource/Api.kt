package im.bernier.movies.datasource

import im.bernier.movies.feature.account.model.AccountResponse
import im.bernier.movies.feature.account.model.ListsItem
import im.bernier.movies.feature.authentication.model.SessionRequest
import im.bernier.movies.feature.authentication.model.SessionResponse
import im.bernier.movies.feature.authentication.model.TokenResponse
import im.bernier.movies.feature.authentication.model.ValidateTokenRequest
import im.bernier.movies.feature.authentication.model.ValidateTokenResponse
import im.bernier.movies.feature.cast.Person
import im.bernier.movies.feature.genre.Genres
import im.bernier.movies.feature.movie.Movie
import im.bernier.movies.feature.movie.Page
import im.bernier.movies.feature.search.SearchResultItem
import im.bernier.movies.feature.tv.TV
import im.bernier.movies.feature.watchlist.model.AddToWatchListResponse
import im.bernier.movies.feature.watchlist.model.WatchlistRequest
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @POST("authentication/token/validate_with_login")
    fun validateToken(
        @Body validateTokenRequest: ValidateTokenRequest,
    ): Single<ValidateTokenResponse>

    @POST("authentication/session/new")
    fun newSession(
        @Body sessionRequest: SessionRequest,
    ): Single<SessionResponse>

    @GET("authentication/token/new")
    fun newToken(): Single<TokenResponse>

    @GET("account")
    fun getAccount(
        @Query("session_id") sessionId: String,
    ): Single<AccountResponse>

    @GET("account/{accountId}/favorite/movies")
    fun getFavoriteMovies(
        @Path("accountId") accountId: String,
    ): Single<Page<Movie>>

    @GET("account/{accountId}/favorite/tv")
    fun getFavoriteTV(
        @Path("accountId") accountId: String,
    ): Single<Page<TV>>

    @GET("account/{accountId}/lists")
    fun getLists(
        @Path("accountId") accountId: String,
    ): Single<Page<ListsItem>>

    @GET("discover/movie")
    fun discover(
        @Query("page") page: Int,
    ): Single<Page<Movie>>

    @GET("discover/tv")
    fun discoverTV(
        @Query("page") page: Int,
    ): Single<Page<TV>>

    @GET("genre/movie/list")
    fun genres(): Call<Genres>

    @GET("movie/{movieId}?append_to_response=credits")
    fun getMovie(
        @Path("movieId") movieId: Long,
    ): Single<Movie>

    @GET("tv/{tvId}?append_to_response=credits")
    fun getTv(
        @Path("tvId") tvId: Long,
    ): Single<TV>

    @GET("search/multi")
    fun search(
        @Query("query") query: String,
    ): Call<Page<SearchResultItem>>

    @GET("person/{person_id}?append_to_response=movie_credits,tv_credits")
    fun getCastById(
        @Path("person_id") id: Long,
    ): Single<Person>

    @POST("account/{account_id}/watchlist")
    fun addToWatchlist(
        @Path("account_id") accountId: String,
        @Query("session_id") sessionId: String,
        @Body watchlistRequest: WatchlistRequest,
    ): Single<AddToWatchListResponse>

    @GET("account/{account_id}/watchlist/movies")
    fun getWatchlistMovies(
        @Path("account_id") accountId: String,
        @Query("session_id") sessionId: String,
    ): Single<Page<Movie>>
}
