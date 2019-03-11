package im.bernier.movies.movie


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import im.bernier.movies.R
import timber.log.Timber

class MovieFragment : Fragment() {

    private lateinit var viewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this)[MovieViewModel::class.java]
        val movie: Long = arguments?.getLong("movie") ?: 0
        viewModel.movieId = movie

        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            Timber.i("Movie : %s", it.id)
        })
    }
}
