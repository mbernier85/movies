package im.bernier.movies.cast

import kotlinx.serialization.Serializable

/**
 * Created by Michael on 2020-02-25.
 */
@Serializable
data class Person(
    val id: Int,
    val name: String,
    val birthday: String?,
    val deathday: String?,
    val biography: String,
    val profile_path: String?
)