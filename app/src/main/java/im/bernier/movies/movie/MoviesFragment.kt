package im.bernier.movies.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.toLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import im.bernier.movies.Api
import im.bernier.movies.Repository
import im.bernier.movies.R

class MoviesFragment : Fragment() {

    private lateinit var api: Api
    private lateinit var adapter: MoviesAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var moviesDataSource: MoviesDataSource? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        api = Repository.api

        val recyclerView: RecyclerView = view!!.findViewById(R.id.recyclerViewMovies)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = MoviesAdapter()
        recyclerView.adapter = adapter

        val sourceFactory = MoviesDataSourceFactory()
        sourceFactory.sourceLiveData.observe(this, Observer {
            moviesDataSource = it
        })
        val liveData = sourceFactory.toLiveData(20)
        liveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            swipeRefreshLayout.isRefreshing = false
        })

        swipeRefreshLayout = view!!.findViewById(R.id.swipeRefreshLayoutMovies)
        swipeRefreshLayout.setOnRefreshListener {
            moviesDataSource?.invalidate()
        }
    }

    companion object {
        fun newInstance(): MoviesFragment {
            return MoviesFragment()
        }
    }

}