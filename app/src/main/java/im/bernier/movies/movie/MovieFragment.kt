package im.bernier.movies.movie


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import im.bernier.movies.R
import im.bernier.movies.cast.Cast
import im.bernier.movies.cast.CastAdapter
import im.bernier.movies.cast.CastFragment.Companion.ARG_CAST_ID
import im.bernier.movies.databinding.FragmentMovieBinding
import im.bernier.movies.util.showError
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber

@AndroidEntryPoint
class MovieFragment : androidx.fragment.app.Fragment() {

    private val viewModel: MovieViewModel by viewModels()
    private lateinit var binding: FragmentMovieBinding
    private val compositeDisposable = CompositeDisposable()
    private lateinit var adapter: CastAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.recyclerViewCasts
        adapter = CastAdapter(listOf(), 5) {
            if (it != null) {
                showCastMember(it)
            } else {
                showFullCastList()
            }
        }
        binding.viewModel = viewModel
        binding.executePendingBindings()
        val movie: Long = arguments?.getLong("movie") ?: 0
        viewModel.movieId = movie

        recyclerView.isNestedScrollingEnabled = false
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        compositeDisposable.add(
            viewModel.observableMovie().observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewModel.movie = it
                    adapter.update(it.credits.cast)
                    binding.invalidateAll()
                }, {
                    Timber.e(it)
                    showError(R.string.network_error)
                })
        )
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    fun showFullCastList() {

    }

    private fun showCastMember(cast: Cast) {
        findNavController().navigate(R.id.action_movieFragment_to_castFragment, Bundle().apply {
            putLong(ARG_CAST_ID, cast.id)
        })
    }
}
