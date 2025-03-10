package im.bernier.movies.feature.credits

import im.bernier.movies.feature.cast.Cast
import im.bernier.movies.feature.crew.Crew
import kotlinx.serialization.Serializable

@Serializable
data class Credits(
    val id: Int = 0,
    val cast: List<Cast> = listOf(),
    val crew: List<Crew> = listOf(),
)
