package im.bernier.movies.movie

import im.bernier.movies.credits.Credits
import im.bernier.movies.genre.Genre
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val title: String = "",
    val id: Long = 0,
    val backdrop_path: String = "",
    val poster_path: String = "",
    val overview: String = "",
    val genre_ids: List<Int> = listOf(),
    var genreString: String = "",
    val genres: List<Genre> = listOf(),
    val credits: Credits = Credits()
)