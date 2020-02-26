package im.bernier.movies.cast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import im.bernier.movies.R
import im.bernier.movies.databinding.FragmentCastBinding
import im.bernier.movies.datasource.Repository

const val ARG_CAST_ID = "castId"

class CastFragment : Fragment() {
    private var castId: Int? = null
    private val viewModel: CastViewModel by viewModels()
    private lateinit var binding: FragmentCastBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            castId = savedInstanceState?.getInt(ARG_CAST_ID) ?: it.getInt(ARG_CAST_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_cast, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        castId?.let {
            Repository.fetchPerson(it)
        }
        Repository.person.observe(viewLifecycleOwner, Observer {
            viewModel.update(it)
        })

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        castId?.let {
            outState.putInt(ARG_CAST_ID, it)
        }
    }
}
