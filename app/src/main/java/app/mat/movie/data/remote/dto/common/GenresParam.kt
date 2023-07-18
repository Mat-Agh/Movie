package app.mat.movie.data.remote.dto.common

data class GenresParam(
    private val genres: List<GenreDto>
) {
    override fun toString(): String {
        return genres.distinct().map { genre ->
            genre.id
        }.joinToString(separator = "|")
    }
}
