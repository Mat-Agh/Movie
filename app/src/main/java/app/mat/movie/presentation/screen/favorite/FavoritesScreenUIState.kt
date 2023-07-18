package app.mat.movie.presentation.screen.favorite

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import app.mat.movie.common.type.FavoriteType
import app.mat.movie.data.remote.dto.common.Presentable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class FavoritesScreenUIState(
    val selectedFavouriteType: FavoriteType,
    val favorites: Flow<PagingData<Presentable>>,
) {
    companion object {
        val default: FavoritesScreenUIState = FavoritesScreenUIState(
            selectedFavouriteType = FavoriteType.Movie,
            favorites = emptyFlow()
        )
    }
}