package im.bernier.movies.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.scopes.ActivityRetainedScoped
import im.bernier.movies.datasource.local.AppDatabase
import im.bernier.movies.datasource.local.Storage
import im.bernier.movies.datasource.remote.Api
import im.bernier.movies.feature.account.model.AccountResponse
import im.bernier.movies.feature.authentication.model.SessionRequest
import im.bernier.movies.feature.authentication.model.SessionResponse
import im.bernier.movies.feature.authentication.model.TokenResponse
import im.bernier.movies.feature.authentication.model.ValidateTokenRequest
import im.bernier.movies.feature.authentication.model.ValidateTokenResponse
import im.bernier.movies.feature.cast.Person
import im.bernier.movies.feature.movie.Movie
import im.bernier.movies.feature.movie.Page
import im.bernier.movies.feature.search.SearchResultItem
import im.bernier.movies.feature.tv.TV
import im.bernier.movies.feature.watchlist.MovieWatchListItem
import im.bernier.movies.feature.watchlist.model.AddToWatchListResponse
import im.bernier.movies.feature.watchlist.model.WatchlistRequest
import retrofit2.Call
import retrofit2.Callback
import timber.log.Timber
import javax.inject.Inject

@ActivityRetainedScoped
class Repository
    @Inject
    constructor(
        private val api: Api,
        private val db: AppDatabase,
        private val storage: Storage,
    ) {
        private val searchLiveData = MutableLiveData<List<SearchResultItem>>()

        val searchResult: LiveData<List<SearchResultItem>>
            get() = searchLiveData

        val loggedIn: Boolean
            get() = storage.getSessionId().isNotEmpty()

        suspend fun validateToken(
            token: String,
            username: String,
            password: String,
        ): ValidateTokenResponse = api.validateToken(ValidateTokenRequest(username, password, token))

        suspend fun newSession(token: String): SessionResponse =
            api
                .newSession(SessionRequest(token))
                .also {
                    storage.setSessionId(it.session_id)
                }

        suspend fun getAccount(): AccountResponse =
            api
                .getAccount(storage.getSessionId())
                .also {
                    storage.setAccountId(it.id.toString())
                }

        suspend fun login(): TokenResponse = api.newToken()

        suspend fun fetchMovie(id: Long): Movie = api.getMovie(id)

        suspend fun fetchTv(id: Long): TV = api.getTv(id)

        suspend fun fetchGenres() {
            api.genres().genres.also {
                db.genreDao().insertAll(it)
            }
        }

        suspend fun getCastById(id: Long): Person = api.getCastById(id)

        suspend fun discover(page: Int): Page<Movie> =
            api.discover(page).also {
                it.results.forEach { movie ->
                    movie.genreString =
                        db
                            .genreDao()
                            .loadAllByIds(movie.genre_ids.toIntArray())
                            .joinToString { genre -> genre.name }
                }
            }

        suspend fun discoverTV(page: Int): Page<TV> =
            api.discoverTV(page).also {
                it.results.forEach { tv ->
                    tv.genreString =
                        db
                            .genreDao()
                            .loadAllByIds(tv.genre_ids.toIntArray())
                            .joinToString { genre -> genre.name }
                }
            }

        fun search(query: String) {
            api.search(query).enqueue(
                object : Callback<Page<SearchResultItem>?> {
                    override fun onFailure(
                        call: Call<Page<SearchResultItem>?>,
                        t: Throwable,
                    ) {
                        Timber.e(t)
                    }

                    override fun onResponse(
                        call: Call<Page<SearchResultItem>?>,
                        response: retrofit2.Response<Page<SearchResultItem>?>,
                    ) {
                        response.body()?.let {
                            searchLiveData.postValue(it.results)
                        }
                    }
                },
            )
        }

        suspend fun watchList(
            accountId: String,
            sessionId: String,
        ): Page<Movie> =
            api
                .getWatchlistMovies(accountId = accountId, sessionId = sessionId)
                .also {
                    db.watchListDao().insertMovieWatchList(
                        it.results.map { movie ->
                            MovieWatchListItem(movie.id)
                        },
                    )
                }

        suspend fun addToWatchList(
            mediaId: Long,
            watchlist: Boolean,
            mediaType: String,
        ): AddToWatchListResponse =
            api
                .addToWatchlist(
                    accountId = storage.getAccountId(),
                    sessionId = storage.getSessionId(),
                    watchlistRequest =
                        WatchlistRequest(
                            media_id = mediaId,
                            watchlist = watchlist,
                            media_type = mediaType,
                        ),
                )

        suspend fun logout() {
            try {
                api.deleteSession(sessionId = storage.getSessionId())
            } catch (e: Exception) {
                Timber.e(e)
            } finally {
                storage.clear()
            }
        }
    }
