package app.mat.movie.presentation.component.item

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun ReviewItemPlaceholder(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
) {
    Box(
        modifier = modifier.placeholder(
            visible = true,
            highlight = PlaceholderHighlight.shimmer(
                highlightColor = MaterialTheme.colors.primary,
                animationSpec = InfiniteRepeatableSpec(
                    animation = tween(
                        durationMillis = 500,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                )
            ),
            color = MaterialTheme.colors.surface,
            shape = shape
        )
    )
}