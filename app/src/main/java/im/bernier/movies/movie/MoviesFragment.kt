package im.bernier.movies.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import im.bernier.movies.R
import im.bernier.movies.databinding.FragmentMoviesBinding
import im.bernier.movies.util.showError
import timber.log.Timber

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
    ): View {
        binding = FragmentMoviesBinding.inflate(layoutInflater)
        val view = binding.root
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutMovies)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewMovies)
        recyclerView.layoutManager = LinearLayoutManager(context)

        setupAdapter()
        recyclerView.adapter = adapter

        setupLiveData()
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_moviesFragment_to_searchFragment)
        }
        return view
    }

    private fun setupLiveData() {
        viewModel.sourceFactory.sourceLiveData.observe(viewLifecycleOwner, {
            viewModel.moviesDataSource = it
        })

        viewModel.errors.observe(viewLifecycleOwner, {
            Timber.e(it)
            showError(R.string.network_error)
        })

        viewModel.liveData.observe(viewLifecycleOwner, {
            adapter.submitList(it)
            swipeRefreshLayout.isRefreshing = false
        })

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun setupAdapter() {
        adapter = MoviesAdapter {
            val bundle = bundleOf("movie" to it)
            findNavController().navigate(R.id.action_moviesFragment_to_movieFragment, bundle)
        }
    }

}