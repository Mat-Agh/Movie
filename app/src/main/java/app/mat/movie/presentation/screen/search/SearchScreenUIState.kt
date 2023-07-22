package app.mat.movie.presentation.screen.search

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import app.mat.movie.data.remote.dto.common.Presentable
import app.mat.movie.data.remote.dto.common.SearchResultDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class SearchScreenUIState(
    val searchOptionsState: SearchOptionsState,
    val query: String?,
    val suggestions: List<String>,
    val searchState: SearchState,
    val resultState: ResultState,
    val queryLoading: Boolean
) {
    companion object {
        val default: SearchScreenUIState = SearchScreenUIState(
            searchOptionsState = SearchOptionsState.default,
            query = null,
            suggestions = emptyList(),
            searchState = SearchState.EmptyQuery,
            resultState = ResultState.Default(),
            queryLoading = false
        )
    }
}

@Stable
data class SearchOptionsState(
    val voiceSearchAvailable: Boolean,
    val cameraSearchAvailable: Boolean
) {
    companion object {
        val default: SearchOptionsState = SearchOptionsState(
            voiceSearchAvailable = false,
            cameraSearchAvailable = false
        )
    }
}

sealed class SearchState {
    data object EmptyQuery : SearchState()
    data object InsufficientQuery : SearchState()
    data object ValidQuery : SearchState()
}

sealed class ResultState {
    data class Default(
        val popular: Flow<PagingData<Presentable>> = emptyFlow()
    ) : ResultState()

    data class Search(
        val result: Flow<PagingData<SearchResultDto>>
    ) : ResultState()
}

data class QueryState(
    val query: String?,
    val loading: Boolean
) {
    companion object {
        val default: QueryState = QueryState(
            query = null,
            loading = false
        )
    }
}