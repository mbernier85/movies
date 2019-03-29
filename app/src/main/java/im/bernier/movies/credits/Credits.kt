package im.bernier.movies.credits

import im.bernier.movies.cast.Cast
import im.bernier.movies.crew.Crew

data class Credits(
    val id: Int = 0,
    val cast: List<Cast> = listOf(),
    val crew: List<Crew> = listOf()
)