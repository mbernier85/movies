package im.bernier.movies.movie

import kotlinx.serialization.Serializable

@Serializable
data class Page(val page: Int, val total_results: Int, val total_pages: Int, val results: List<Movie>)