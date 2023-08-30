package app.mat.movie.domain.useCase.common

interface MediaSearchQueriesUseCase {
    suspend operator fun invoke(
        query: String
    ): List<String>
}