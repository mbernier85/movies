package im.bernier.movies.feature.movie

import im.bernier.movies.feature.credits.Credits
import im.bernier.movies.feature.genre.Genre
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val title: String = "",
    val id: Long = 0,
    val backdrop_path: String? = null,
    val poster_path: String = "",
    val overview: String = "",
    val genre_ids: List<Int> = listOf(),
    var genreString: String = "",
    val genres: List<Genre> = listOf(),
    val credits: Credits = Credits(),
)
