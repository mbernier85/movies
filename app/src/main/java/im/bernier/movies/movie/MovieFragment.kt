package im.bernier.movies.movie


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import im.bernier.movies.R
import im.bernier.movies.cast.ARG_CAST_ID
import im.bernier.movies.cast.Cast
import im.bernier.movies.cast.CastAdapter
import im.bernier.movies.databinding.FragmentMovieBinding

class MovieFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModels()
    private lateinit var binding: FragmentMovieBinding

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
        val adapter = CastAdapter(listOf(), 5) {
            if (it != null) {
                showCastMember(it)
            } else {
                showFullCastList()
            }
        }
        binding.viewModel = viewModel
        binding.executePendingBindings()
        val movie: Int = arguments?.getInt("movie") ?: 0
        viewModel.movieId = movie

        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            viewModel.movie = it
            adapter.update(it.credits.cast)
            binding.invalidateAll()
        })

        recyclerView.isNestedScrollingEnabled = false
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    fun showFullCastList() {

    }

    private fun showCastMember(cast: Cast) {
        findNavController().navigate(R.id.action_movieFragment_to_castFragment, Bundle().apply {
            putInt(ARG_CAST_ID, cast.id)
        })
    }
}
