package im.bernier.movies.di

fun interface AssistedViewModelFactory<T> {
    fun create(id: Long): T
}