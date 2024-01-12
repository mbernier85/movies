package im.bernier.movies.feature.crew

import kotlinx.serialization.Serializable

@Serializable
data class Crew(
    val credit_id: String,
    val department: String,
    val gender: Int?,
    val id: Int,
    val job: String,
    val name: String,
    val profile_path: String?
)