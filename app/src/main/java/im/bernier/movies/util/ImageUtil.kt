package im.bernier.movies.util

fun String.imageUrl(): String {
    return "https://image.tmdb.org/t/p/original$this"
}