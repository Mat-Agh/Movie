package app.mat.movie.presentation.component.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.mat.movie.common.type.FavoriteType
import app.mat.movie.presentation.theme.spacing

@Composable
fun FavoriteTypeButton(
    modifier: Modifier = Modifier,
    type: FavoriteType,
    selected: Boolean = false,
    onClick: () -> Unit = {}
) {
    val textColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.primary
        } else MaterialTheme.colorScheme.onBackground, label = ""
    )
    val lineLength = animateFloatAsState(
        targetValue = if (selected) 2f else 0f,
        animationSpec = tween(
            durationMillis = 200
        ),
        label = ""
    )
    val currentOption = MaterialTheme.colorScheme.primary
    Text(
        modifier = modifier
            .padding(
                MaterialTheme.spacing.medium
            )
            .drawBehind {
                if (lineLength.value > 0f) {
                    drawLine(
                        color = if (selected) {
                            currentOption
                        } else {
                            Color.Magenta
                        },
                        start = Offset(
                            size.width / 2f - lineLength.value * 10.dp.toPx(),
                            size.height
                        ),
                        end = Offset(
                            size.width / 2f + lineLength.value * 10.dp.toPx(),
                            size.height
                        ),
                        strokeWidth = 5.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }
            .clickable(
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                }) {
                onClick()
            },
        text = stringResource(
            type.getLabelResourceId()
        ),
        color = textColor,
        style = MaterialTheme.typography.headlineMedium
    )
}