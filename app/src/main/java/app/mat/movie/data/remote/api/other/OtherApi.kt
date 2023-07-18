package app.mat.movie.data.remote.api.other

import app.mat.movie.data.remote.dto.common.CollectionResponseDto
import app.mat.movie.data.remote.dto.common.CombinedCreditsDto
import app.mat.movie.data.remote.dto.common.ConfigDto
import app.mat.movie.data.remote.dto.common.ExternalIdsDto
import app.mat.movie.data.remote.dto.common.PersonDetailsDto
import app.mat.movie.data.remote.dto.common.SearchResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OtherApi {
    @GET("configuration")
    fun getConfig(): Call<ConfigDto>

    @GET("search/multi")
    suspend fun multiSearch(
        @Query("page") page: Int,
        @Query("language") standardCode: String,
        @Query("region") region: String,
        @Query("query") query: String,
        @Query("year") year: Int?,
        @Query("include_adult") includeAdult: Boolean,
        @Query("primary_release_year") releaseYear: Int?
    ): SearchResponseDto

    @GET("collection/{collection_id}")
    fun getCollection(
        @Path("collection_id") collectionId: Int,
        @Query("language") standardCode: String
    ): Call<CollectionResponseDto>

    @GET("person/{person_id}")
    fun getPersonDetails(
        @Path("person_id") personId: Int,
        @Query("language") standardCode: String
    ): Call<PersonDetailsDto>

    @GET("person/{person_id}/combined_credits")
    fun getCombinedCredits(
        @Path("person_id") personId: Int,
        @Query("language") standardCode: String
    ): Call<CombinedCreditsDto>

    @GET("person/{person_id}/external_ids")
    fun getPersonExternalIds(
        @Path("person_id") personId: Int,
        @Query("language") standardCode: String
    ): Call<ExternalIdsDto>
}