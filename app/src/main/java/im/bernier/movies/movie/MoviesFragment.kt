package im.bernier.movies.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import im.bernier.movies.R
import im.bernier.movies.databinding.FragmentMoviesBinding
import kotlinx.android.synthetic.main.fragment_movies.view.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var binding: FragmentMoviesBinding
    private lateinit var adapter: MoviesAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(layoutInflater)
        val view = binding.root
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutMovies)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewMovies)
        recyclerView.layoutManager = LinearLayoutManager(context)

        setupAdapter()
        recyclerView.adapter = adapter

        setupLiveData()
        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_moviesFragment_to_searchFragment)
        }
        return view
    }

    private fun setupLiveData() {
        viewModel.sourceFactory.sourceLiveData.observe(viewLifecycleOwner, Observer {
            viewModel.moviesDataSource = it
        })

        viewModel.errors.observe(viewLifecycleOwner, Observer {
            Timber.e(it)
            showError()
        })

        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            swipeRefreshLayout.isRefreshing = false
        })

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun showError() {
        Snackbar.make(binding.root, R.string.network_error, Snackbar.LENGTH_LONG).show()
    }

    private fun setupAdapter() {
        adapter = MoviesAdapter {
            val bundle = bundleOf("movie" to it)
            findNavController().navigate(R.id.action_moviesFragment_to_movieFragment, bundle)
        }
    }

}