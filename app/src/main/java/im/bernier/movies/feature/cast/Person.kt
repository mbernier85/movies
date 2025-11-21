package im.bernier.movies.feature.cast

import kotlinx.serialization.Serializable

/**
 * Created by Michael on 2020-02-25.
 */
@Serializable
data class Person(
    val id: Long = 0,
    val name: String = "",
    val birthday: String? = null,
    val deathday: String? = null,
    val biography: String = "",
    val profile_path: String? = null,
    val movie_credits: Casts? = null,
    val tv_credits: Casts? = null,
)

@Serializable
data class Casts(
    val cast: List<Cast>?,
)
