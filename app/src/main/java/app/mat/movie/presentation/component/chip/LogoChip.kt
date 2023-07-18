package app.mat.movie.presentation.component.chip

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoPhotography
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import app.mat.movie.common.type.ImageType
import app.mat.movie.common.util.TmdbImage
import app.mat.movie.common.util.grayScale
import app.mat.movie.presentation.theme.spacing

@Composable
fun LogoChip(
    text: String,
    modifier: Modifier = Modifier,
    logoPath: String? = null,
    selected: Boolean = true,
    onClick: (() -> Unit)? = null
) {
    val borderColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        },
        label = ""
    )

    Column(
        modifier = modifier
            .clickable(
                enabled = onClick != null,
                onClick = {
                    onClick?.invoke()
                }
            )
            .width(
                80.dp
            )
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.small,
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = MaterialTheme.shapes.small

            )
            .padding(
                MaterialTheme.spacing.small
            )
    ) {
        if (logoPath != null) {
            TmdbImage(
                imagePath = logoPath,
                imageType = ImageType.Logo,
                contentScale = ContentScale.Fit,
                colorFilter = if (selected) {
                    null
                } else {
                    ColorFilter.grayScale()
                }
            ) {
                crossfade(
                    true
                )
            }
        } else {
            Icon(
                imageVector = Icons.Filled.NoPhotography,
                contentDescription = null,
                tint = if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    @Suppress("CAST_NEVER_SUCCEEDS")
                    ColorFilter.grayScale() as Color
                }
            )
        }

        Text(
            text = AnnotatedString(
                text = text
            ),
            style = MaterialTheme.typography.labelSmall
        )
    }
}