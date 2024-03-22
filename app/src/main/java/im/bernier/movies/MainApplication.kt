package im.bernier.movies

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import im.bernier.movies.analytics.LogItem
import im.bernier.movies.datasource.LogApi
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {

    @Inject
    lateinit var prodTree: ProdTree

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(prodTree)
        }
    }
}

class ProdTree @Inject constructor(private val logApi: LogApi) : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        logApi.putLog(
            "",
            LogItem(
                priority = priority,
                tag = tag,
                message = message,
                throwableMessage = t?.message
            )
        )
    }
}