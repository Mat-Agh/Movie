package app.mat.movie.presentation.component.section

import android.annotation.SuppressLint
import android.util.Size
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.paging.compose.LazyPagingItems
import app.mat.movie.R
import app.mat.movie.common.type.ImageType
import app.mat.movie.common.util.BottomRoundedArcShape
import app.mat.movie.common.util.getMaxSizeInt
import app.mat.movie.common.util.isDark
import app.mat.movie.common.util.rememberTmdbImagePainter
import app.mat.movie.data.remote.dto.common.DetailPresentable
import app.mat.movie.data.remote.dto.common.DetailPresentableItemState
import app.mat.movie.presentation.component.item.PresentableTopSectionItem
import app.mat.movie.presentation.theme.sizes
import app.mat.movie.presentation.theme.spacing
import coil.compose.AsyncImagePainter
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint(
    "UnrememberedMutableState"
)
@Composable
fun PresentableTopSection(
    title: String,
    state: LazyPagingItems<out DetailPresentable>,
    modifier: Modifier = Modifier,
    showMoreButton: Boolean = true,
    scrollState: ScrollState? = null,
    scrollValueLimit: Float? = null,
    onPresentableClick: (Int) -> Unit = {},
    onMoreClick: () -> Unit = {}
) {
    val density = LocalDensity.current
    val pagerState = rememberPagerState {
        state.itemCount
    }
    var isDark by rememberSaveable { mutableStateOf(true) }
    val contentColor by animateColorAsState(
        targetValue = if (isDark) {
            Color.White
        } else {
            Color.Black
        },
        label = ""
    )
    val selectedPresentable by derivedStateOf {
        val snapShot = state.itemSnapshotList
        if (snapShot.isNotEmpty()) snapShot.getOrNull(
            pagerState.currentPage
        ) else null
    }
    val currentScrollValue = scrollState?.value
    val ratio = if (currentScrollValue != null && scrollValueLimit != null) {
        (currentScrollValue / scrollValueLimit).coerceIn(0f, 1f)
    } else 0f
    val itemHeight = density.run { MaterialTheme.sizes.presentableItemBig.height.toPx() }

    Box(
        modifier = Modifier.clip(
            RectangleShape
        )
    ) {
        BoxWithConstraints(
            modifier = modifier
                .matchParentSize()
                .graphicsLayer {
                    clip = true
                    shape = BottomRoundedArcShape(
                        ratio = ratio
                    )
                }
        ) {
            val (maxWidth, maxHeight) = getMaxSizeInt()
            Crossfade(
                targetState = selectedPresentable,
                modifier = Modifier.fillMaxSize(), label = ""
            ) { movie ->
                val backdropScale = remember {
                    androidx.compose.animation.core.Animatable(1f)
                }
                val backgroundPainter = rememberTmdbImagePainter(
                    path = movie?.backdropPath,
                    type = ImageType.Backdrop,
                    preferredSize = Size(
                        maxWidth,
                        maxHeight
                    ),
                    builder = {
                        allowHardware(
                            false
                        )
                    }
                )
                val backgroundPainterState = backgroundPainter.state

                LaunchedEffect(
                    backgroundPainterState
                ) {
                    if (backgroundPainterState is AsyncImagePainter.State.Success) {
                        backgroundPainterState.result.drawable.toBitmap().run {
                            isDark = this.isDark()
                        }

                        backdropScale.animateTo(
                            targetValue = 1.6f,
                            animationSpec = tween(
                                durationMillis = 10000,
                                easing = LinearEasing
                            )
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .align(
                            Alignment.BottomCenter
                        )
                        .matchParentSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.background
                                )
                            )
                        )
                )
                Image(
                    modifier = Modifier
                        .blur(
                            8.dp
                        )
                        .fillMaxSize()
                        .scale(
                            backdropScale.value
                        )
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.background
                                )
                            )
                        ),
                    painter = backgroundPainter,
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight
                )
            }
        }
        Box(
            modifier = Modifier
                .align(
                    Alignment.BottomCenter
                )
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
        )
        Box(
            modifier = Modifier
                .align(
                    Alignment.BottomCenter
                )
                .fillMaxWidth()
                .height(
                    160.dp
                )
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.spacing.medium
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = contentColor,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                if (showMoreButton) {
                    TextButton(
                        onClick = onMoreClick
                    ) {
                        Text(
                            text = stringResource(
                                R.string.movies_more
                            ),
                            color = contentColor,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                }
            }
            HorizontalPager(
                state = pagerState
            ) { page ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            MaterialTheme.spacing.medium
                        )
                ) {
                    val presentable = try {
                        state[page]
                    } catch (e: IndexOutOfBoundsException) {
                        null
                    }

                    val presentableItemState = presentable?.let {
                        DetailPresentableItemState.Result(it)
                    } ?: DetailPresentableItemState.Loading

                    PresentableTopSectionItem(
                        modifier = Modifier.fillMaxWidth(),
                        presentableItemState = presentableItemState,
                        isSelected = selectedPresentable == presentable,
                        contentColor = contentColor,
                        onPresentableClick = {
                            presentable?.let {
                                onPresentableClick(
                                    it.id
                                )
                            }
                        },
                        itemTransformations = {
                            val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

                            androidx.compose.ui.layout.lerp(
                                start = ScaleFactor(
                                    0.7f,
                                    0.7f
                                ),
                                stop = ScaleFactor(
                                    1f,
                                    1f
                                ),
                                fraction = 1f - pageOffset.coerceIn(
                                    0f,
                                    1f
                                )
                            ).also { scale ->
                                scaleX = scale.scaleX
                                scaleY = scale.scaleY

                                translationY = itemHeight * (1f - scale.scaleY) / 2
                            }

                            alpha = androidx.compose.ui.layout.lerp(
                                start = ScaleFactor(
                                    0.3f,
                                    0.3f
                                ),
                                stop = ScaleFactor(
                                    1f,
                                    1f
                                ),
                                fraction = 1f - pageOffset.coerceIn(
                                    0f,
                                    1f
                                )
                            ).scaleX
                        },
                        contentTransformations = {
                            val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

                            alpha = androidx.compose.ui.layout.lerp(
                                start = ScaleFactor(
                                    0.1f,
                                    0.1f
                                ),
                                stop = ScaleFactor(
                                    1f,
                                    1f
                                ),
                                fraction = 1f - 2 * pageOffset.coerceIn(
                                    0f,
                                    1f
                                )
                            ).scaleX
                        }
                    )
                }
            }
        }
    }
}