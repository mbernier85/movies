package im.bernier.movies.feature.account.model

import kotlinx.serialization.Serializable

@Serializable
data class AccountResponse(
    val id: Int = 0,
    val name: String = "",
    val username: String = "",
)
