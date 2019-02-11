package im.bernier.movies.movie

data class Movie(val title: String, val id: Long, val backdrop_path: String, val poster_path: String, val overview: String, val genre_ids: List<Int>, var genres: String)