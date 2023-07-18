package app.mat.movie.presentation.component.chip

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import app.mat.movie.presentation.theme.spacing

@Composable
fun IdChip(
    @DrawableRes drawableRes: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(modifier = modifier
        .clip(CircleShape)
        .clickable { onClick() }
        .padding(MaterialTheme.spacing.extraSmall)
    ) {
        Image(
            painter = painterResource(drawableRes),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
    }

}