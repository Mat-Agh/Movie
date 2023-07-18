package app.mat.movie.presentation.screen.details.movie

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import app.mat.movie.data.remote.dto.common.CreditsDto
import app.mat.movie.data.remote.dto.common.ImageDto
import app.mat.movie.data.remote.dto.common.VideoDto
import app.mat.movie.data.remote.dto.common.WatchProvidersDto
import app.mat.movie.data.remote.dto.movie.MovieCollectionDto
import app.mat.movie.data.remote.dto.movie.MovieDetailsDto
import app.mat.movie.data.remote.dto.movie.MovieDto
import app.mat.movie.data.remote.type.ExternalIdsResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import java.util.Date

@Stable
data class MovieDetailsScreenUIState(
    val startRoute: String,
    val movieDetails: MovieDetailsDto?,
    val additionalMovieDetailsInfo: AdditionalMovieDetailsInfo,
    val associatedMovies: AssociatedMovies,
    val associatedContent: AssociatedContent,
    val error: String?
) {
    companion object {
        fun getDefault(
            startRoute: String
        ): MovieDetailsScreenUIState = MovieDetailsScreenUIState(
            startRoute = startRoute,
            movieDetails = null,
            additionalMovieDetailsInfo = AdditionalMovieDetailsInfo.default,
            associatedMovies = AssociatedMovies.default,
            associatedContent = AssociatedContent.default,
            error = null
        )
    }
}

@Stable
data class AssociatedMovies(
    val collection: MovieCollectionDto?,
    val similar: Flow<PagingData<MovieDto>>,
    val recommendations: Flow<PagingData<MovieDto>>,
    val directorMovies: DirectorMovies
) {
    companion object {
        val default: AssociatedMovies = AssociatedMovies(
            collection = null,
            similar = emptyFlow(),
            recommendations = emptyFlow(),
            directorMovies = DirectorMovies.default
        )
    }
}

@Stable
data class DirectorMovies(
    val directorName: String,
    val movies: Flow<PagingData<MovieDto>>
) {
    companion object {
        val default: DirectorMovies = DirectorMovies(
            directorName = "",
            movies = emptyFlow()
        )
    }
}

@Stable
data class AdditionalMovieDetailsInfo(
    val isFavorite: Boolean,
    val watchAtTime: Date?,
    val watchProviders: WatchProvidersDto?,
    val credits: CreditsDto?,
    val reviewsCount: Int
) {
    companion object {
        val default: AdditionalMovieDetailsInfo = AdditionalMovieDetailsInfo(
            isFavorite = false,
            watchAtTime = null,
            watchProviders = null,
            credits = null,
            reviewsCount = 0
        )
    }
}

@Stable
data class AssociatedContent(
    val backdrops: List<ImageDto>,
    val videos: List<VideoDto>?,
    val externalIds: List<ExternalIdsResource>?
) {
    companion object {
        val default: AssociatedContent = AssociatedContent(
            backdrops = emptyList(),
            videos = null,
            externalIds = null
        )
    }
}