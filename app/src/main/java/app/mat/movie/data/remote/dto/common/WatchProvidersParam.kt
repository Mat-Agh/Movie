package app.mat.movie.data.remote.dto.common

data class WatchProvidersParam(
    private val watchProviders: List<ProviderSourceDto>
) {
    override fun toString(): String {
        return watchProviders.distinct().map { provider -> provider.providerId }
            .joinToString(separator = "|")
    }
}
