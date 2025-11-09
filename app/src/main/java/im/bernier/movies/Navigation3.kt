package im.bernier.movies

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import im.bernier.movies.feature.account.AccountRoute
import im.bernier.movies.feature.authentication.LoginRoute
import im.bernier.movies.feature.cast.CastScreen
import im.bernier.movies.feature.cast.CastViewModel
import im.bernier.movies.feature.discover.DiscoverScreen
import im.bernier.movies.feature.movie.MovieScreen
import im.bernier.movies.feature.movie.MovieViewModel
import im.bernier.movies.feature.search.SearchScreen
import im.bernier.movies.feature.tv.TvShowScreen
import im.bernier.movies.feature.tv.TvShowViewModel
import im.bernier.movies.feature.watchlist.WatchListRoute
import kotlinx.serialization.Serializable

@Serializable
data object DiscoverRoute

@Serializable
data class MovieRoute(
    val id: Long,
)

@Serializable
data class CastRoute(
    val id: Long,
)

@Serializable
data class ShowRoute(
    val id: Long,
)

@Serializable
data object LoginRoute

@Serializable
data object ProfileRoute

@Serializable
data object SearchRoute

@Serializable
data object WatchListRoute

@Composable
fun MovieApp(
    onBack: () -> Unit,
    onForward: (Any) -> Unit,
    onTitleChange: (String) -> Unit,
    backStack: List<Any>,
) {
    NavDisplay(
        entryDecorators =
            listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
        backStack = backStack,
        onBack = onBack,
        entryProvider = { key ->
            when (key) {
                is DiscoverRoute -> {
                    NavEntry(key) {
                        DiscoverScreen(
                            onTitleChanged = onTitleChange,
                            onNavigateToMovie = {
                                onForward(MovieRoute(it))
                            },
                            onNavigateToShow = {
                                onForward(ShowRoute(it))
                            }
                        )
                    }
                }

                is MovieRoute -> {
                    NavEntry(key) {
                        MovieScreen(
                            viewModel = hiltViewModel(
                                creationCallback = { factory: MovieViewModel.MovieViewModelFactory ->
                                    factory.create(key.id)
                                },
                            ),
                            onNavigateToCast = {
                                onForward(CastRoute(it))
                            },
                            onTitleChanged = onTitleChange,
                        )
                    }
                }

                is ShowRoute -> {
                    NavEntry(key) {
                        TvShowScreen(
                            viewModel = hiltViewModel(
                                creationCallback = { factory: TvShowViewModel.ShowViewModelFactory ->
                                    factory.create(key.id)
                                }
                            ),
                            onTitleChanged = onTitleChange
                        )
                    }
                }

                is CastRoute -> {
                    NavEntry(key) {
                        CastScreen(
                            viewModel = hiltViewModel(
                                creationCallback = { factory: CastViewModel.ModelFactory ->
                                    factory.create(key.id)
                                },
                            ),
                            onTitleChanged = onTitleChange,
                        )
                    }
                }

                is LoginRoute -> {
                    NavEntry(key) {
                        LoginRoute(
                            onTitleChanged = onTitleChange
                        )
                    }
                }

                is ProfileRoute -> {
                    NavEntry(key) {
                        AccountRoute(
                            onTitleChanged = onTitleChange
                        )
                    }
                }

                is SearchRoute -> {
                    NavEntry(key) {
                        SearchScreen(
                            onNavigateToMovie = {
                                onForward(MovieRoute(it))
                            },
                            onNavigateToCast = {
                                onForward(CastRoute(it))
                            },
                            onTitleChanged = onTitleChange,
                            onNavigateToTvShow = {
                                onForward(ShowRoute(it))
                            }
                        )
                    }
                }

                is WatchListRoute -> {
                    NavEntry(key) {
                        WatchListRoute(

                        )
                    }
                }


                else -> {
                    NavEntry(Unit) {
                        Text(text = "Invalid Key: $it")
                    }
                }
            }
        },
    )
}
