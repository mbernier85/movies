package im.bernier.movies.cast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Michael on 2020-02-24.
 */
@HiltViewModel
class CastViewModel @Inject constructor(): ViewModel() {
    private val personLiveData = MutableLiveData<Person>()
    val person: LiveData<Person>
        get() = personLiveData

    fun update(person: Person) {
        personLiveData.value = person
    }
}