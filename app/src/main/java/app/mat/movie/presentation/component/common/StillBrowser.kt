package app.mat.movie.presentation.component.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import app.mat.movie.common.type.ImageType
import app.mat.movie.common.util.TmdbImage
import app.mat.movie.data.remote.dto.common.ImageDto
import coil.size.Scale
import coil.size.Size
import com.google.accompanist.pager.HorizontalPagerIndicator

@OptIn(
    ExperimentalFoundationApi::class
)
@Composable
fun StillBrowser(
    modifier: Modifier = Modifier,
    stillPaths: List<ImageDto>
) {
    val pagerState = rememberPagerState {
        stillPaths.count()
    }

    Column(
        modifier = modifier
    ) {
        stillPaths.count()
        HorizontalPager(
            modifier = modifier,
            state = pagerState
        ) { page ->
            val stillImage = stillPaths.getOrNull(
                page
            )
            TmdbImage(
                modifier = Modifier.fillMaxWidth(),
                imagePath = stillImage?.filePath,
                imageType = ImageType.Still,
                contentScale = ContentScale.FillWidth
            ) {
                size(
                    size = Size.ORIGINAL
                )
                scale(
                    scale = Scale.FIT
                )
                crossfade(
                    enable = true
                )
            }

            if (stillPaths.count() > 1) {
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    pageCount = stillPaths.count(),
                    modifier = modifier
                        .align(
                            Alignment.CenterHorizontally
                        )
                        .padding(
                            16.dp
                        )
                )
            }
        }
    }
}