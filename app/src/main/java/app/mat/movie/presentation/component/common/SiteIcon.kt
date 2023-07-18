package app.mat.movie.presentation.component.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import app.mat.movie.R
import app.mat.movie.data.remote.type.VideoSiteDto

@Composable
fun SiteIcon(
    site: VideoSiteDto,
    modifier: Modifier = Modifier
) {
    @DrawableRes
    val drawableRes = when (site) {
        VideoSiteDto.YouTube -> R.drawable.ic_youtube
        VideoSiteDto.Vimeo -> R.drawable.ic_vimeo
    }

    Image(
        modifier = modifier,
        painter = painterResource(
            drawableRes
        ),
        contentDescription = null
    )
}