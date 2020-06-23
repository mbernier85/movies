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
import dagger.hilt.android.AndroidEntryPoint
import im.bernier.movies.R
import im.bernier.movies.cast.CastFragment.Companion.ARG_CAST_ID
import im.bernier.movies.databinding.FragmentSearchBinding
import im.bernier.movies.datasource.Repository
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    @Inject
    lateinit var repository: Repository

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        adapter = SearchResultAdapter(listOf()) { resultItem ->
            when (resultItem.media_type) {
                "movie", "tv" -> findNavController().navigate(
                    R.id.movieFragment,
                    Bundle().apply { putLong("movie", resultItem.id) })
                "person" -> findNavController().navigate(
                    R.id.castFragment,
                    Bundle().apply { putLong(ARG_CAST_ID, resultItem.id) })
            }
        }
        binding.recyclerViewSearchResult.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewSearchResult.adapter = adapter
        repository.searchResult.observe(viewLifecycleOwner, Observer {
            adapter.update(it)
        })
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
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
}
