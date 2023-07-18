package app.mat.movie.presentation.component.item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import app.mat.movie.data.remote.dto.common.DetailPresentableItemState
import app.mat.movie.presentation.theme.ComposableSize
import app.mat.movie.presentation.theme.sizes

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun DetailPresentableItem(
    presentableState: DetailPresentableItemState,
    modifier: Modifier = Modifier,
    size: ComposableSize = MaterialTheme.sizes.presentableItemSmall,
    selected: Boolean = false,
    showTitle: Boolean = true,
    showScore: Boolean = false,
    showAdult: Boolean = false,
    transformations: GraphicsLayerScope.() -> Unit = {},
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .width(
                size.width
            )
            .aspectRatio(
                size.ratio
            )
            .graphicsLayer {
                transformations()
            },
        shape = MaterialTheme.shapes.medium,
        border = if (selected) {
            BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            null
        }
    ) {
        when (presentableState) {
            is DetailPresentableItemState.Loading -> {
                LoadingPresentableItem(
                    modifier = modifier.fillMaxSize()
                )
            }

            is DetailPresentableItemState.Error -> {
                ErrorPresentableItem(
                    modifier = modifier.fillMaxSize()
                )
            }

            is DetailPresentableItemState.Result -> {
                ResultDetailPresentableItem(
                    modifier = modifier.fillMaxSize(),
                    presentable = presentableState.presentable,
                    showScore = showScore,
                    showTitle = showTitle,
                    showAdult = showAdult,
                    onClick = onClick
                )
            }
        }
    }
}