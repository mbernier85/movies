package im.bernier.movies.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import im.bernier.movies.R
import im.bernier.movies.databinding.FragmentMoviesBinding
import kotlinx.android.synthetic.main.fragment_movies.view.*

class MoviesFragment : Fragment() {

    private lateinit var viewModel: MoviesViewModel
    private lateinit var adapter: MoviesAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentMoviesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false)
        val view = binding.root
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutMovies)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewMovies)
        recyclerView.layoutManager = LinearLayoutManager(context)

        setupAdapter()
        recyclerView.adapter = adapter

        viewModel = ViewModelProviders.of(this)[MoviesViewModel::class.java]
        setupLiveData()
        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_moviesFragment_to_searchFragment)
        }
        return view
    }

    private fun setupLiveData() {
        viewModel.sourceFactory.sourceLiveData.observe(this, Observer {
            viewModel.moviesDataSource = it
        })

        viewModel.liveData.observe(viewLifecycleOwner, Observer {
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