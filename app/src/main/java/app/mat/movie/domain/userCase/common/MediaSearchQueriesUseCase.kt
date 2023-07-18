package app.mat.movie.domain.userCase.common

interface MediaSearchQueriesUseCase {
    suspend operator fun invoke(
        query: String
    ): List<String>
}