package app.mat.movie.data.repository

import app.mat.movie.data.remote.dto.common.CombinedCreditsDto
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.ExternalIdsDto
import app.mat.movie.data.remote.dto.common.PersonDetailsDto
import retrofit2.Call

interface PersonRepository {
    fun getPersonDetails(
        personId: Int,
        deviceLanguage: DeviceLanguageDto = DeviceLanguageDto.default
    ): Call<PersonDetailsDto>

    fun getCombinedCredits(
        personId: Int,
        deviceLanguage: DeviceLanguageDto = DeviceLanguageDto.default
    ): Call<CombinedCreditsDto>

    fun getExternalIds(
        personId: Int,
        deviceLanguage: DeviceLanguageDto = DeviceLanguageDto.default
    ): Call<ExternalIdsDto>
}