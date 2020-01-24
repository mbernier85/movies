package im.bernier.movies.search

import kotlinx.serialization.Serializable

/**
 * Created by Michael on 2020-01-22.
 */
@Serializable
data class SearchResultItem(
    val media_type: String,
    val name: String? = null,
    val title: String? = null,
    val poster_path: String? = null,
    val profile_path: String? = null
)