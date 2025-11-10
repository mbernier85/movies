package im.bernier.movies.feature.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.DiscoverRoute
import im.bernier.movies.datasource.Repository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    repository: Repository,
) : ViewModel() {
    val loggedIn: Boolean = repository.loggedIn
    val backStack = mutableStateListOf<Any>(DiscoverRoute)
}
