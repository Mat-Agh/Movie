package app.mat.movie.data.remote.api.other

import app.mat.movie.data.remote.dto.common.CollectionResponseDto
import app.mat.movie.data.remote.dto.common.CombinedCreditsDto
import app.mat.movie.data.remote.dto.common.ConfigDto
import app.mat.movie.data.remote.dto.common.ExternalIdsDto
import app.mat.movie.data.remote.dto.common.PersonDetailsDto
import app.mat.movie.data.remote.dto.common.SearchResponseDto
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OtherApiHelperImpl @Inject constructor(
    private val otherApi: OtherApi
) : OtherApiHelper {
    override fun getConfig(): Call<ConfigDto> = otherApi.getConfig()

    override suspend fun multiSearch(
        page: Int,
        standardCode: String,
        region: String,
        query: String,
        includeAdult: Boolean,
        year: Int?,
        releaseYear: Int?
    ): SearchResponseDto = otherApi.multiSearch(
        page = page,
        standardCode = standardCode,
        region = region,
        query = query,
        includeAdult = includeAdult,
        year = year,
        releaseYear = releaseYear
    )

    override fun getCollection(
        collectionId: Int,
        standardCode: String
    ): Call<CollectionResponseDto> = otherApi.getCollection(
        collectionId,
        standardCode
    )

    override fun getPersonDetails(
        personId: Int,
        standardCode: String
    ): Call<PersonDetailsDto> = otherApi.getPersonDetails(
        personId,
        standardCode
    )

    override fun getCombinedCredits(
        personId: Int,
        standardCode: String
    ): Call<CombinedCreditsDto> = otherApi.getCombinedCredits(
        personId,
        standardCode
    )

    override fun getPersonExternalIds(
        personId: Int,
        standardCode: String
    ): Call<ExternalIdsDto> = otherApi.getPersonExternalIds(
        personId,
        standardCode
    )
}