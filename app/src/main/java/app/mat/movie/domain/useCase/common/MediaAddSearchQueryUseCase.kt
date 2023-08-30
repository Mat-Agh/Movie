package app.mat.movie.domain.useCase.common

import app.mat.movie.data.local.entity.search.SearchQueryEntity

interface MediaAddSearchQueryUseCase {
    operator fun invoke(
        searchQuery: SearchQueryEntity
    )
}