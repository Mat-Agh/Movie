package app.mat.movie.presentation.component.common

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import app.mat.movie.common.type.ImageType
import app.mat.movie.common.util.getMaxSizeInt
import app.mat.movie.common.util.rememberTmdbImagePainter
import coil.size.Scale

@SuppressLint(
    "UnrememberedMutableState"
)
@Composable
fun AnimatedBackdrops(
    paths: List<String>,
    modifier: Modifier = Modifier
) {
    var currentBackdropPathIndex by remember {
        mutableIntStateOf(0)
    }
    val currentBackdropPath by derivedStateOf {
        paths.getOrNull(0)
    }
    BoxWithConstraints(
        modifier = modifier
    ) {
        val (maxWidth, maxHeight) = getMaxSizeInt()

        Crossfade(
            modifier = Modifier.fillMaxSize(),
            animationSpec = tween(1000),
            targetState = currentBackdropPath,
            label = ""
        ) { path ->
            val backgroundPainter = rememberTmdbImagePainter(
                path = path,
                type = ImageType.Backdrop,
                preferredSize = android.util.Size(
                    maxWidth,
                    maxHeight
                ),
                builder = {
                    size(
                        coil.size.Size.ORIGINAL
                    )
                    scale(
                        Scale.FILL
                    )
                    transformations(
                    )
                }
            )
            val backdropScale = remember {
                Animatable(
                    1f
                )
            }
            LaunchedEffect(
                path
            ) {
                val result = backdropScale.animateTo(
                    targetValue = 1.6f,
                    animationSpec = tween(
                        durationMillis = 10000,
                        easing = LinearEasing
                    )
                )

                when (result.endReason) {
                    AnimationEndReason.Finished -> {
                        val backdropCount = paths.count()
                        val nextIndex = currentBackdropPathIndex + 1

                        currentBackdropPathIndex = if (nextIndex >= backdropCount) {
                            0
                        } else {
                            nextIndex
                        }
                    }

                    else -> Unit
                }
            }
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .scale(
                        backdropScale.value
                    ),
                painter = backgroundPainter,
                contentDescription = null,
                contentScale = ContentScale.FillHeight
            )
        }
    }
}