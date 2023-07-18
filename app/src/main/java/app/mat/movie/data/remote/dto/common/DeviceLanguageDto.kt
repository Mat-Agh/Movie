package app.mat.movie.data.remote.dto.common

data class DeviceLanguageDto(
    val region: String,
    val languageCode: String
) {
    companion object {
        val default: DeviceLanguageDto = DeviceLanguageDto(
            region = "US",
            languageCode = "en-US"
        )
    }
}