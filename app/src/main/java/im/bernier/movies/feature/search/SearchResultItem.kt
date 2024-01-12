package im.bernier.movies.feature.search

import kotlinx.serialization.Serializable

/**
 * Created by Michael on 2020-01-22.
 */
@Serializable
data class SearchResultItem(
    val id: Long,
    val media_type: String,
    val name: String? = null,
    val title: String? = null,
    val poster_path: String? = null,
    val profile_path: String? = null
)