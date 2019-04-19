package im.bernier.movies.genre

import kotlinx.serialization.Serializable

@Serializable
data class Genres(val genres: List<Genre>)