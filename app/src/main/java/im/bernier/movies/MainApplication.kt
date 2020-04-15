package im.bernier.movies

import android.app.Application
import im.bernier.movies.di.AppComponent
import im.bernier.movies.di.AppModule
import im.bernier.movies.di.DaggerAppComponent
import timber.log.Timber

class MainApplication: Application() {

    private lateinit var _appComponent: AppComponent

    val appComponent
        get() = _appComponent

    override fun onCreate() {
        super.onCreate()
        _appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        Timber.plant(Timber.DebugTree())
    }
}