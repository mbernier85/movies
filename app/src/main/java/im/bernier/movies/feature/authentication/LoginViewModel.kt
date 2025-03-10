package im.bernier.movies.feature.authentication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import im.bernier.movies.datasource.Repository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        val repository: Repository,
    ) : ViewModel() {
        var uiState = mutableStateOf(UiState())
        private val compositeDisposable = CompositeDisposable()

        fun login(
            username: String,
            password: String,
        ) {
            val disposable =
                repository
                    .login()
                    .observeOn(Schedulers.io())
                    .flatMap {
                        repository.validateToken(it.request_token, username, password)
                    }.flatMap { repository.newSession(it.request_token) }
                    .flatMap { repository.getAccount() }
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        uiState.value = uiState.value.copy(success = true)
                    }, {
                        Timber.e(it)
                    })
            compositeDisposable.add(disposable)
        }

        override fun onCleared() {
            super.onCleared()
            compositeDisposable.clear()
        }
    }

data class UiState(
    val success: Boolean = false,
)
