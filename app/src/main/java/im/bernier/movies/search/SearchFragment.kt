package im.bernier.movies.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import im.bernier.movies.databinding.FragmentSearchBinding
import im.bernier.movies.datasource.Repository

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater,container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        adapter = SearchResultAdapter(listOf())
        binding.recyclerViewSearchResult.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewSearchResult.adapter = adapter
        Repository.searchResult.observe(viewLifecycleOwner, Observer {
            adapter.update(it)
        })
        return binding.root
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}
