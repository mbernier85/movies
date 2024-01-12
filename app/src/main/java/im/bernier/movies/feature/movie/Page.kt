package im.bernier.movies.feature.movie

import kotlinx.serialization.Serializable

@Serializable
data class Page<T>(val page: Int, val total_results: Int, val total_pages: Int, val results: List<T>)