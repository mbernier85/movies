package im.bernier.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.transaction
import im.bernier.movies.movie.MoviesFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.findFragmentByTag("movies") == null) {
            supportFragmentManager.transaction(now = true, allowStateLoss = false) {
                add(R.id.frameLayoutMain, MoviesFragment.newInstance(), "movies")
            }
        }

        Repository.fetchGenres()
    }
}
