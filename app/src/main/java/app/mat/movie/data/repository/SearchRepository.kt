package app.mat.movie.data.repository

import androidx.paging.PagingData
import app.mat.movie.data.local.entity.search.SearchQueryEntity
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.SearchResultDto
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun multiSearch(
        query: String,
        includeAdult: Boolean = false,
        year: Int? = null,
        releaseYear: Int? = null,
        deviceLanguage: DeviceLanguageDto = DeviceLanguageDto.default
    ): Flow<PagingData<SearchResultDto>>

    suspend fun searchQueries(
        query: String
    ): List<String>

    fun addSearchQuery(
        searchQuery: SearchQueryEntity
    )
}