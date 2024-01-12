package im.bernier.movies.feature.genre

import kotlinx.serialization.Serializable

@Serializable
data class Genres(val genres: List<Genre>)