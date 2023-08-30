package app.mat.movie.presentation.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import app.mat.movie.common.type.FavoriteType
import app.mat.movie.domain.useCase.common.GetFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(
    private val getFavouritesUseCaseImpl: GetFavoritesUseCase
) : ViewModel() {
    //region Variables
    private val _selectedFavouriteType: MutableStateFlow<FavoriteType> = MutableStateFlow(
        FavoriteType.Movie
    )

    @OptIn(
        ExperimentalCoroutinesApi::class
    )
    val uiState: StateFlow<FavoritesScreenUIState> = _selectedFavouriteType.mapLatest { type ->
        val favorites = getFavouritesUseCaseImpl(
            type
        ).cachedIn(
            viewModelScope
        )

        FavoritesScreenUIState(
            selectedFavouriteType = type,
            favorites = favorites
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        FavoritesScreenUIState.default
    )
    //endregion Variables

    //region Public Methods
    fun onFavoriteTypeSelected(
        type: FavoriteType
    ) {
        viewModelScope.launch {
            _selectedFavouriteType.emit(
                type
            )
        }
    }
    //endregion Public Methods
}