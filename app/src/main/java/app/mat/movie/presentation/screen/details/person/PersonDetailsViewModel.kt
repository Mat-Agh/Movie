package app.mat.movie.presentation.screen.details.person

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import app.mat.movie.common.onError
import app.mat.movie.common.onException
import app.mat.movie.common.onSuccess
import app.mat.movie.data.remote.dto.common.CombinedCreditsDto
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.ExternalIdsDto
import app.mat.movie.data.remote.dto.common.PersonDetailsDto
import app.mat.movie.data.remote.type.ExternalContentType
import app.mat.movie.data.remote.type.ExternalIdsResource
import app.mat.movie.domain.userCase.common.GetCombinedCreditsUseCase
import app.mat.movie.domain.userCase.common.GetDeviceLanguageUseCase
import app.mat.movie.domain.userCase.common.GetPersonDetailsUseCase
import app.mat.movie.domain.userCase.common.GetPersonExternalIdsUseCase
import app.mat.movie.presentation.navigation.screen.ApplicationGraphScreen.Companion.PersonDetailsScreenData
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen
import app.mat.movie.presentation.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailsViewModel @Inject constructor(
    getDeviceLanguageUseCase: GetDeviceLanguageUseCase,
    private val getPersonDetailsUseCase: GetPersonDetailsUseCase,
    private val getCombinedCreditsUseCase: GetCombinedCreditsUseCase,
    private val getPersonExternalIdsUseCase: GetPersonExternalIdsUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    //region Variables
    private val personId: Int = savedStateHandle[PersonDetailsScreenData.PERSON_DETAILS_REQUIRED_ARGUMENT_PERSON_ID] ?: 0

    private val startRoute: String = savedStateHandle[PersonDetailsScreenData.PERSON_DETAILS_REQUIRED_ARGUMENT_START_ROUTE] ?: NavigationBarGraphScreen.MovieScreen.route

    private val deviceLanguage: Flow<DeviceLanguageDto> = getDeviceLanguageUseCase()

    private val personDetails: MutableStateFlow<PersonDetailsDto?> = MutableStateFlow(
        null
    )
    private val combinedCredits: MutableStateFlow<CombinedCreditsDto?> = MutableStateFlow(
        null
    )
    private val _externalIds: MutableStateFlow<ExternalIdsDto?> = MutableStateFlow(
        null
    )

    @OptIn(
        ExperimentalCoroutinesApi::class
    )
    private val externalIds: StateFlow<List<ExternalIdsResource>?> = _externalIds.filterNotNull().mapLatest { externalIds ->
        externalIds.toExternalIdList(
            type = ExternalContentType.Person
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(
            10
        ), null
    )

    val uiState: StateFlow<PersonDetailsScreenUIState> = combine(
        personDetails,
        combinedCredits,
        externalIds,
        error
    ) { details, combinedCredits, externalIds, error ->
        PersonDetailsScreenUIState(
            startRoute = startRoute,
            details = details,
            externalIds = externalIds,
            credits = combinedCredits,
            error = error
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        PersonDetailsScreenUIState.getDefault()
    )
    //endregion Variables

    //region Initialization
    init {
        getPersonInfo()
    }
    //endregion Initialization

    //region Private Methods
    private fun getPersonInfo() = viewModelScope.launch {
        deviceLanguage.collectLatest { deviceLanguage ->
            launch {
                getPersonDetails(
                    personId = personId,
                    deviceLanguage = deviceLanguage
                )
            }

            launch {
                getCombinedCredits(
                    personId = personId,
                    deviceLanguage = deviceLanguage
                )
            }

            launch {
                getExternalIds(
                    personId = personId,
                    deviceLanguage = deviceLanguage
                )
            }
        }
    }

    private suspend fun getPersonDetails(
        personId: Int,
        deviceLanguage: DeviceLanguageDto
    ) = getPersonDetailsUseCase(
        personId = personId, deviceLanguage = deviceLanguage
    ).onSuccess {
        viewModelScope.launch {
            personDetails.emit(
                data
            )
        }
    }.onError {
        onError(
            this
        )
    }.onException {
        onException(
            this
        )
    }

    private suspend fun getCombinedCredits(
        personId: Int,
        deviceLanguage: DeviceLanguageDto
    ) =
        getCombinedCreditsUseCase(
            personId = personId,
            deviceLanguage = deviceLanguage
        ).onSuccess {
            viewModelScope.launch {
                combinedCredits.emit(
                    data
                )
            }
        }.onError {
            onError(
                this
            )
        }.onException {
            onException(
                this
            )
        }

    private suspend fun getExternalIds(
        personId: Int,
        deviceLanguage: DeviceLanguageDto
    ) = getPersonExternalIdsUseCase(
        personId = personId,
        deviceLanguage = deviceLanguage
    ).onSuccess {
        viewModelScope.launch {
            _externalIds.emit(
                data
            )
        }
    }.onError {
        onError(
            this
        )
    }.onException {
        onException(
            this
        )
    }
//endregion Private Methods
}