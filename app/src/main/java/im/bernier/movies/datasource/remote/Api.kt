package im.bernier.movies.datasource.remote

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
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @POST("authentication/token/validate_with_login")
    suspend fun validateToken(
        @Body validateTokenRequest: ValidateTokenRequest,
    ): ValidateTokenResponse

    @POST("authentication/session/new")
    suspend fun newSession(
        @Body sessionRequest: SessionRequest,
    ): SessionResponse

    @DELETE("authentication/session")
    suspend fun deleteSession(): SessionResponse

    @GET("authentication/token/new")
    suspend fun newToken(): TokenResponse

    @GET("account")
    suspend fun getAccount(
        @Query("session_id") sessionId: String,
    ): AccountResponse

    @GET("account/{accountId}/favorite/movies")
    suspend fun getFavoriteMovies(
        @Path("accountId") accountId: String,
    ): Page<Movie>

    @GET("account/{accountId}/favorite/tv")
    suspend fun getFavoriteTV(
        @Path("accountId") accountId: String,
    ): Page<TV>

    @GET("account/{accountId}/lists")
    suspend fun getLists(
        @Path("accountId") accountId: String,
    ): Page<ListsItem>

    @GET("discover/movie")
    suspend fun discover(
        @Query("page") page: Int,
    ): Page<Movie>

    @GET("discover/tv")
    suspend fun discoverTV(
        @Query("page") page: Int,
    ): Page<TV>

    @GET("genre/movie/list")
    suspend fun genres(): Genres

    @GET("movie/{movieId}?append_to_response=credits")
    suspend fun getMovie(
        @Path("movieId") movieId: Long,
    ): Movie

    @GET("tv/{tvId}?append_to_response=credits")
    suspend fun getTv(
        @Path("tvId") tvId: Long,
    ): TV

    @GET("search/multi")
    fun search(
        @Query("query") query: String,
    ): Call<Page<SearchResultItem>>

    @GET("person/{person_id}?append_to_response=movie_credits,tv_credits")
    suspend fun getCastById(
        @Path("person_id") id: Long,
    ): Person

    @POST("account/{account_id}/watchlist")
    suspend fun addToWatchlist(
        @Path("account_id") accountId: String,
        @Query("session_id") sessionId: String,
        @Body watchlistRequest: WatchlistRequest,
    ): AddToWatchListResponse

    @GET("account/{account_id}/watchlist/movies")
    suspend fun getWatchlistMovies(
        @Path("account_id") accountId: String,
        @Query("session_id") sessionId: String,
    ): Page<Movie>
}
