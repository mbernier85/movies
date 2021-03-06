package im.bernier.movies.cast

import kotlinx.serialization.Serializable

@Serializable
data class Cast(
    val cast_id: Int,
    val character: String,
    val credit_id: String,
    val gender: Int?,
    val id: Long,
    val name: String,
    val order: Int,
    val profile_path: String?
)