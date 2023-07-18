package app.mat.movie.data.remote.api.other

import app.mat.movie.data.remote.dto.common.CollectionResponseDto
import app.mat.movie.data.remote.dto.common.CombinedCreditsDto
import app.mat.movie.data.remote.dto.common.ConfigDto
import app.mat.movie.data.remote.dto.common.DeviceLanguageDto
import app.mat.movie.data.remote.dto.common.ExternalIdsDto
import app.mat.movie.data.remote.dto.common.PersonDetailsDto
import app.mat.movie.data.remote.dto.common.SearchResponseDto
import retrofit2.Call

interface OtherApiHelper {
    fun getConfig(): Call<ConfigDto>

    suspend fun multiSearch(
        page: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode,
        region: String = DeviceLanguageDto.default.region,
        query: String,
        includeAdult: Boolean = false,
        year: Int? = null,
        releaseYear: Int? = null
    ): SearchResponseDto

    fun getCollection(
        collectionId: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode
    ): Call<CollectionResponseDto>

    fun getPersonDetails(
        personId: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode
    ): Call<PersonDetailsDto>

    fun getCombinedCredits(
        personId: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode
    ): Call<CombinedCreditsDto>

    fun getPersonExternalIds(
        personId: Int,
        standardCode: String = DeviceLanguageDto.default.languageCode
    ): Call<ExternalIdsDto>
}