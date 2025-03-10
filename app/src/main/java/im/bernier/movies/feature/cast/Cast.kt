package im.bernier.movies.feature.cast

import kotlinx.serialization.Serializable

@Serializable
data class Cast(
    val character: String,
    val credit_id: String,
    val id: Long,
    val title: String = "",
    val name: String = "",
    val cast_id: Int? = null,
    val gender: Int? = null,
    val profile_path: String? = null,
    val order: Int? = null,
)
