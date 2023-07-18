package app.mat.movie.domain.userCase.common

import app.mat.movie.data.repository.SearchRepository
import javax.inject.Inject

class MediaSearchQueriesUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
) : MediaSearchQueriesUseCase {
    override suspend operator fun invoke(
        query: String
    ): List<String> = searchRepository.searchQueries(
        query
    )
}