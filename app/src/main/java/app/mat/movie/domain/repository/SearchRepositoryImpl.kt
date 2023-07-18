package app.mat.movie.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import app.mat.movie.data.local.database.dao.search.SearchQueryDao
import app.mat.movie.data.local.entity.search.SearchQueryEntity
import app.mat.movie.data.paging.other.SearchResponsePagingDataSource
import app.mat.movie.data.remote.api.other.OtherApiHelper
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.SearchResultDto
import app.mat.movie.data.repository.SearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepositoryImpl @Inject constructor(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val externalScope: CoroutineScope,
    private val otherApiHelper: OtherApiHelper,
    private val searchQueryDao: SearchQueryDao
) : SearchRepository {
    override fun multiSearch(
        query: String,
        includeAdult: Boolean,
        year: Int?,
        releaseYear: Int?,
        deviceLanguage: DeviceLanguageDto
    ): Flow<PagingData<SearchResultDto>> = Pager(
        PagingConfig(
            pageSize = 20
        )
    ) {
        SearchResponsePagingDataSource(
            otherApiHelper = otherApiHelper,
            includeAdult = includeAdult,
            query = query,
            year = year,
            releaseYear = releaseYear,
            language = deviceLanguage.languageCode
        )
    }.flow.flowOn(
        defaultDispatcher
    )

    override suspend fun searchQueries(
        query: String
    ): List<String> {
        return searchQueryDao.searchQueries(
            query
        )
    }

    override fun addSearchQuery(
        searchQuery: SearchQueryEntity
    ) {
        externalScope.launch(
            defaultDispatcher
        ) {
            searchQueryDao.addQuery(
                searchQuery
            )
        }
    }
}