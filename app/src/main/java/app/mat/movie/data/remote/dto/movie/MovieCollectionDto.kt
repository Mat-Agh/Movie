package app.mat.movie.data.remote.dto.movie

import app.mat.movie.data.remote.dto.common.PartDto

data class MovieCollectionDto(
    val name: String,
    val parts: List<PartDto>
)
