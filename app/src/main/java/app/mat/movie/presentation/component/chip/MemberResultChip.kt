package app.mat.movie.presentation.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import app.mat.movie.common.type.ImageType
import app.mat.movie.common.util.TmdbImage
import app.mat.movie.presentation.theme.spacing
import coil.transform.CircleCropTransformation

@Composable
fun MemberResultChip(
    modifier: Modifier = Modifier,
    profilePath: String?,
    firstLine: String?,
    secondLine: String?,
    onClick: () -> Unit = {}
) {
    var secondLineExpanded by remember {
        mutableStateOf(
            false
        )
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            MaterialTheme.spacing.extraSmall
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (profilePath != null) {
            TmdbImage(
                modifier = modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = CircleShape
                    )
                    .aspectRatio(
                        1f
                    )
                    .clip(CircleShape)
                    .clickable { onClick() },
                imagePath = profilePath,
                imageType = ImageType.Profile
            ) {
                transformations(CircleCropTransformation())
                crossfade(
                    true
                )
            }
        } else {
            MemberNoPhotoChip(
                onClick = onClick
            )
        }
        firstLine?.let { firstLine ->
            if (firstLine.isNotBlank()) {
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = AnnotatedString(
                        text = firstLine
                    ),
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }
        secondLine?.let { secondLine ->
            if (secondLine.isNotBlank()) {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            secondLineExpanded = !secondLineExpanded
                        },
                    text = AnnotatedString(
                        text = secondLine
                    ),
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = if (secondLineExpanded) Int.MAX_VALUE else 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}