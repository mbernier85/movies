package im.bernier.movies.feature.account.model

import kotlinx.serialization.Serializable

@Serializable
data class ListsItem(
    val id: Int,
    val description: String = "",
    val favorite_count: Int = 0,
    val item_count: Int = 0,
    val list_type: String = "",
    val name: String = "",
    val poster_path: String = "",
)
