package im.bernier.movies.analytics

import kotlinx.serialization.Serializable

@Serializable
data class LogItem(
    val priority: Int,
    val tag: String?,
    val message: String,
    val throwableMessage: String?,
)