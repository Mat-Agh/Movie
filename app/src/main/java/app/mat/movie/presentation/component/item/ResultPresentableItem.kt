package app.mat.movie.presentation.component.item

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import app.mat.movie.common.type.ImageType
import app.mat.movie.common.util.TmdbImage
import app.mat.movie.data.remote.dto.common.Presentable
import app.mat.movie.presentation.theme.spacing
import coil.size.Scale

@SuppressLint(
    "UnrememberedMutableState"
)
@Composable
fun ResultPresentableItem(
    presentable: Presentable,
    modifier: Modifier = Modifier,
    showTitle: Boolean = true,
    onClick: (() -> Unit)? = null
) {
    val hasPoster by derivedStateOf {
        presentable.posterPath != null
    }

    Box(modifier = modifier
        .clickable(
            enabled = onClick != null,
            onClick = {
                onClick?.invoke()
            }
        )
    ) {
        if (hasPoster) {
            TmdbImage(
                modifier = Modifier.matchParentSize(),
                imagePath = presentable.posterPath,
                imageType = ImageType.Poster
            ) {
                size(
                    coil.size.Size.ORIGINAL
                )
                scale(
                    Scale.FILL
                )
                crossfade(
                    true
                )
            }
        } else {
            NoPhotoPresentableItem(
                modifier = Modifier.fillMaxSize()
            )
        }

        if (showTitle) {
            Box(
                modifier = Modifier
                    .align(
                        Alignment.BottomCenter
                    )
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.surface
                    )
            ) {
                Text(
                    modifier = Modifier
                        .align(
                            Alignment.BottomStart
                        )
                        .padding(
                            MaterialTheme.spacing.extraSmall
                        ),
                    text = presentable.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            }
        }
    }
}