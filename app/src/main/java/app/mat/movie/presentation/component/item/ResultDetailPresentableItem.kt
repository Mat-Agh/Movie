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
import androidx.compose.ui.text.style.TextOverflow
import app.mat.movie.common.type.ImageType
import app.mat.movie.common.util.TmdbImage
import app.mat.movie.data.remote.dto.common.DetailPresentable
import app.mat.movie.presentation.component.chip.AdultChips
import app.mat.movie.presentation.theme.spacing
import coil.size.Scale

@SuppressLint(
    "UnrememberedMutableState"
)
@Composable
fun ResultDetailPresentableItem(
    presentable: DetailPresentable,
    modifier: Modifier = Modifier,
    showScore: Boolean = false,
    showTitle: Boolean = true,
    showAdult: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val hasPoster by derivedStateOf {
        presentable.posterPath != null
    }

    Box(
        modifier = modifier
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
                    style = MaterialTheme.typography.bodySmall

                )
            }
        }

        if (presentable.voteCount > 0 && showScore) {
            MovplayPresentableScoreItem(
                modifier = Modifier
                    .align(
                        Alignment.TopEnd
                    )
                    .padding(
                        end = MaterialTheme.spacing.extraSmall,
                        top = MaterialTheme.spacing.extraSmall
                    ),
                score = presentable.voteAverage,
            )
        }

        if (presentable.adult == true && showAdult) {
            AdultChips(
                modifier = Modifier
                    .align(
                        Alignment.BottomStart
                    )
                    .padding(
                        start = MaterialTheme.spacing.extraSmall,
                        bottom = MaterialTheme.spacing.extraSmall
                    )
            )
        }
    }
}