package im.bernier.movies.feature.tv

import im.bernier.movies.feature.credits.Credits
import im.bernier.movies.feature.genre.Genre
import kotlinx.serialization.Serializable

@Serializable
data class TV(
    val name: String = "",
    val id: Long = 0,
    val backdrop_path: String? = null,
    val poster_path: String = "",
    val overview: String = "",
    val genre_ids: List<Int> = listOf(),
    var genreString: String = "",
    val genres: List<Genre> = listOf(),
    val credits: Credits = Credits(),
    val first_air_date: String = "",
    val original_name: String = ""
)