package im.bernier.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import im.bernier.movies.datasource.Repository
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as MainApplication).appComponent.inject(this)
        repository.fetchGenres()
    }
}
