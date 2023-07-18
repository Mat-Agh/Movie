package app.mat.movie.presentation.component.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AnimatedContentContainer(
    modifier: Modifier = Modifier,
    visible: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit = {}
) {
    AnimatedVisibility(
        modifier = modifier,
        enter = fadeIn(
            tween(
                300,
                delayMillis = 100
            )
        ) + expandVertically(
            tween(
                300
            )
        ),
        exit = fadeOut(
            tween(
                300
            )
        ) + shrinkVertically(
            tween(
                300,
                delayMillis = 100
            )
        ),
        visible = visible,
        content = content
    )
}