package im.bernier.movies.movie

data class Page(val page: Int, val total_results: Int, val total_pages: Int, val results: List<Movie>)