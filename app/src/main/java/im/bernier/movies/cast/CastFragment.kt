package im.bernier.movies.cast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import im.bernier.movies.MainApplication
import im.bernier.movies.R
import im.bernier.movies.databinding.FragmentCastBinding
import im.bernier.movies.datasource.Repository
import im.bernier.movies.di.ViewModelFactory
import javax.inject.Inject

class CastFragment : Fragment() {

    private var castId: Long? = null
    private val viewModel: CastViewModel by viewModels(factoryProducer = { viewModelFactory })
    private lateinit var binding: FragmentCastBinding

    @Inject
    lateinit var repository: Repository
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            castId = savedInstanceState?.getLong(ARG_CAST_ID) ?: it.getLong(ARG_CAST_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity?.application as MainApplication).appComponent.inject(this)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_cast, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        castId?.let {
            repository.fetchPerson(it)
            repository.person(it).observe(viewLifecycleOwner, Observer { person ->
                viewModel.update(person)
            })
        }
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        castId?.let {
            outState.putLong(ARG_CAST_ID, it)
        }
    }

    companion object {
        const val ARG_CAST_ID = "castId"
    }
}
