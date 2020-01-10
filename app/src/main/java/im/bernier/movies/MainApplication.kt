package im.bernier.movies

import android.app.Application
import im.bernier.movies.datasource.Repository
import timber.log.Timber

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        Repository.init(this)
    }
}