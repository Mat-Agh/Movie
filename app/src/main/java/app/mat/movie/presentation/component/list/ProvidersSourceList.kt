package app.mat.movie.presentation.component.list

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.mat.movie.data.remote.dto.common.ProviderSourceDto
import app.mat.movie.presentation.component.chip.LogoChip

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProvidersSourceList(
    selectedProvidersSources: List<ProviderSourceDto>,
    availableProvidersSources: List<ProviderSourceDto>,
    modifier: Modifier = Modifier,
    onProviderSourceSelected: (ProviderSourceDto) -> Unit = {}
) {
    FlowRow(
        modifier = modifier,
    ) {
        availableProvidersSources.map { providerSource ->
            val selected = providerSource in selectedProvidersSources

            LogoChip(
                logoPath = providerSource.logoPath,
                text = providerSource.providerName,
                selected = selected
            ) {
                onProviderSourceSelected(providerSource)
            }
        }
    }
}