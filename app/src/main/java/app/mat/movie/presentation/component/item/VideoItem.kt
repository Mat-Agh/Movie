package app.mat.movie.presentation.component.item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import app.mat.movie.data.remote.dto.common.VideoDto
import app.mat.movie.presentation.component.common.SiteIcon
import app.mat.movie.presentation.theme.sizes
import app.mat.movie.presentation.theme.spacing
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size

@Composable
fun VideoItem(
    video: VideoDto,
    modifier: Modifier = Modifier,
    onVideoClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .width(
                MaterialTheme.sizes.videoItem.width
            )
            .height(
                MaterialTheme.sizes.videoItem.height
            ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        onVideoClick()
                    },
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(
                        LocalContext.current
                    ).data(
                        data = video.getThumbnailUrl()
                    ).apply(
                        block = fun ImageRequest.Builder.() {
                            size(
                                Size.ORIGINAL
                            )
                            scale(
                                Scale.FILL
                            )
                            crossfade(
                                true
                            )
                        }).build()
                ),
                contentScale = ContentScale.FillWidth,
                contentDescription = null
            )
            SiteIcon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        top = MaterialTheme.spacing.extraSmall,
                        end = MaterialTheme.spacing.extraSmall
                    ),
                site = video.site
            )
        }
    }
}