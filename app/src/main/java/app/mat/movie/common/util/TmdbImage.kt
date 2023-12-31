package app.mat.movie.common.util

import android.annotation.SuppressLint
import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import app.mat.movie.common.type.ImageType
import app.mat.movie.common.type.MatchingStrategyType
import app.mat.movie.presentation.view.LocalImageUrlParser
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest


@Composable
inline fun rememberTmdbImagePainter(
    path: String?,
    type: ImageType,
    preferredSize: Size,
    strategy: MatchingStrategyType = MatchingStrategyType.FirstBiggerWidth,
    builder: ImageRequest.Builder.() -> Unit = {},
): AsyncImagePainter {
    val imageUrlParser = LocalImageUrlParser.current

    val imageUrl = imageUrlParser?.getImageUrl(
        path = path,
        type = type,
        preferredSize = preferredSize,
        strategy = strategy
    )

    return rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .apply {
                builder(this)
            }.build()
    )
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun TmdbImage(
    imagePath: String?,
    imageType: ImageType,
    modifier: Modifier = Modifier,
    strategy: MatchingStrategyType = MatchingStrategyType.FirstBiggerWidth,
    contentScale: ContentScale = ContentScale.FillBounds,
    colorFilter: ColorFilter? = null,
    builder: ImageRequest.Builder.() -> Unit = {}
) {
    BoxWithConstraints(modifier = modifier) {
        val (maxWith, maxHeight) = getMaxSizeInt()

        val painter = rememberTmdbImagePainter(
            path = imagePath,
            type = imageType,
            preferredSize = Size(maxWith, maxHeight),
            strategy = strategy
        ) {
            builder()
        }

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = null,
            colorFilter = colorFilter,
            contentScale = contentScale
        )
    }
}