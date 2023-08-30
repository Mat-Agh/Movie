package app.mat.movie.domain.useCase.common

import app.mat.movie.data.local.entity.search.SearchQueryEntity
import app.mat.movie.data.repository.SearchRepository
import javax.inject.Inject

class MediaAddSearchQueryUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
) : MediaAddSearchQueryUseCase {
    override operator fun invoke(
        searchQuery: SearchQueryEntity
    ) = searchRepository.addSearchQuery(
        searchQuery
    )
}