package app.mat.movie.domain.repository

import app.mat.movie.data.remote.api.other.OtherApiHelper
import app.mat.movie.data.remote.dto.common.CombinedCreditsDto
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.ExternalIdsDto
import app.mat.movie.data.remote.dto.common.PersonDetailsDto
import app.mat.movie.data.repository.PersonRepository
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonRepositoryImpl @Inject constructor(
    private val otherApiHelper: OtherApiHelper
) : PersonRepository {
    override fun getPersonDetails(
        personId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Call<PersonDetailsDto> {
        return otherApiHelper.getPersonDetails(
            personId,
            deviceLanguage.languageCode
        )
    }

    override fun getCombinedCredits(
        personId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Call<CombinedCreditsDto> {
        return otherApiHelper.getCombinedCredits(
            personId,
            deviceLanguage.languageCode
        )
    }

    override fun getExternalIds(
        personId: Int,
        deviceLanguage: DeviceLanguageDto
    ): Call<ExternalIdsDto> {
        return otherApiHelper.getPersonExternalIds(
            personId,
            deviceLanguage.languageCode
        )
    }
}