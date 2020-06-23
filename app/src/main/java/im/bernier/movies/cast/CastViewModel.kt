package im.bernier.movies.cast

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

/**
 * Created by Michael on 2020-02-24.
 */
class CastViewModel @ViewModelInject constructor(): ViewModel() {
    private val personLiveData = MutableLiveData<Person>()
    val person: LiveData<Person>
        get() = personLiveData

    fun update(person: Person) {
        personLiveData.value = person
    }
}