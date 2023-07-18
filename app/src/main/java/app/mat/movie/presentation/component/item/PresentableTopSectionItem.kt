package app.mat.movie.presentation.component.item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import app.mat.movie.data.remote.dto.common.DetailPresentableItemState
import app.mat.movie.presentation.theme.ComposableSize
import app.mat.movie.presentation.theme.sizes
import app.mat.movie.presentation.theme.spacing

@Composable
fun PresentableTopSectionItem(
    presentableItemState: DetailPresentableItemState,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    contentColor: Color = Color.White,
    presentableSize: ComposableSize = MaterialTheme.sizes.presentableItemBig,
    onPresentableClick: () -> Unit = {},
    itemTransformations: GraphicsLayerScope.() -> Unit = {},
    contentTransformations: GraphicsLayerScope.() -> Unit = {}
) {
    Row(
        modifier = modifier
    ) {
        DetailPresentableItem(
            presentableState = presentableItemState,
            size = presentableSize,
            showTitle = false,
            showScore = false,
            showAdult = true,
            onClick = onPresentableClick,
            transformations = itemTransformations
        )
        Spacer(
            modifier = Modifier.width(
                MaterialTheme.spacing.medium
            )
        )
        AnimatedVisibility(
            modifier = Modifier.weight(
                1f
            ),
            enter = fadeIn(),
            exit = fadeOut(),
            visible = isSelected
        ) {
            if (presentableItemState is DetailPresentableItemState.Result) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .graphicsLayer { contentTransformations() },
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                ) {
                    Text(
                        text = presentableItemState.presentable.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = contentColor,
                        fontWeight = FontWeight.Bold
                    )
                    presentableItemState.presentable.overView?.let { overView ->
                        Text(
                            text = overView,
                            style = MaterialTheme.typography.titleSmall,
                            color = contentColor,
                            maxLines = 5,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}