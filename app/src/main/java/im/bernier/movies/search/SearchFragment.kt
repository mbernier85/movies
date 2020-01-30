package im.bernier.movies.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import im.bernier.movies.R
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
        adapter = SearchResultAdapter(listOf()) {id ->
            findNavController().navigate(R.id.movieFragment, Bundle().apply { putLong("movie", id) })
        }
        binding.recyclerViewSearchResult.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewSearchResult.adapter = adapter
        Repository.searchResult.observe(viewLifecycleOwner, Observer {
            adapter.update(it)
        })
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.recyclerViewSearchResult.isVisible) {
                    (binding.root as MotionLayout).transitionToStart()
                } else {
                    findNavController().popBackStack()
                }
            }
        })
        return binding.root
    }

    companion object {
        fun newInstance() = SearchFragment()
    }

}
