package im.bernier.movies.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.EntryProviderScope
import dagger.hilt.android.scopes.ActivityRetainedScoped
import im.bernier.movies.datasource.Repository
import im.bernier.movies.feature.authentication.LoginRoute
import im.bernier.movies.feature.discover.DiscoverRoute
import jakarta.inject.Inject

typealias EntryProviderInstaller = EntryProviderScope<Any>.() -> Unit

@ActivityRetainedScoped
class Navigator
    @Inject
    constructor(
        startDestination: Any = DiscoverRoute,
        private val repository: Repository,
    ) {
        val backStack: SnapshotStateList<Any> = mutableStateListOf(startDestination)

        interface RequiresLogin

        private var onLoginSuccessRoute: Any? = null

        fun goTo(destination: Any) {
            if (destination is RequiresLogin && repository.loggedIn.not()) {
                backStack.add(LoginRoute)
                onLoginSuccessRoute = destination
            } else {
                backStack.add(destination)
            }
        }

        fun login() {
            backStack.removeLastOrNull()
            onLoginSuccessRoute?.let {
                backStack.add(it)
                onLoginSuccessRoute = null
            }
        }

        fun goBack() {
            backStack.removeLastOrNull()
        }
    }
