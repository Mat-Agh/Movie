package app.mat.movie.presentation.component.section

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.mat.movie.data.remote.type.ExternalIdsResource
import app.mat.movie.presentation.component.chip.IdChip

@OptIn(
    ExperimentalLayoutApi::class
)
@Composable
fun ExternalIdsSection(
    externalIds: List<ExternalIdsResource>,
    modifier: Modifier = Modifier,
    onExternalIdClick: (ExternalIdsResource) -> Unit = {}
) {
    if (externalIds.isNotEmpty()) {
        FlowRow(
            modifier.wrapContentHeight()
        ) {
            externalIds.map { id ->
                IdChip(
                    drawableRes = id.drawableRes
                ) {
                    onExternalIdClick(id)
                }
            }
        }
    }
}