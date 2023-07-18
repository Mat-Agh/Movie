package app.mat.movie.presentation.screen.details.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoPhotography
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.mat.movie.common.type.ImageType
import app.mat.movie.common.util.TmdbImage
import app.mat.movie.presentation.theme.ComposableSize
import app.mat.movie.presentation.theme.sizes
import app.mat.movie.presentation.theme.spacing
import coil.size.Scale

@Composable
fun CreditsItem(
    posterPath: String?,
    infoText: String?,
    modifier: Modifier = Modifier,
    size: ComposableSize = MaterialTheme.sizes.presentableItemSmall,
    onClick: () -> Unit = {}
) {
    var infoTextExpanded by remember {
        mutableStateOf(
            false
        )
    }

    Column(
        modifier = Modifier.width(
            size.width
        ),
        verticalArrangement = Arrangement.spacedBy(
            MaterialTheme.spacing.small
        )
    ) {
        Card(
            modifier = modifier
                .width(
                    size.width
                )
                .height(
                    size.height
                )
                .clickable {
                    onClick()
                },
            shape = MaterialTheme.shapes.medium
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                if (posterPath != null) {
                    TmdbImage(
                        modifier = modifier.fillMaxSize(),
                        imagePath = posterPath,
                        imageType = ImageType.Profile
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
                    Box(
                        modifier = modifier
                            .fillMaxSize()
                            .background(
                                MaterialTheme.colorScheme.surface
                            )
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = MaterialTheme.shapes.medium
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            imageVector = Icons.Filled.NoPhotography,
                            contentDescription = null
                        )
                    }
                }
            }
        }

        infoText?.let { text ->
            if (text.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            indication = null
                        ) {
                            infoTextExpanded = !infoTextExpanded
                        },
                    text = text,
                    textAlign = TextAlign.Center,
                    maxLines = if (infoTextExpanded) {
                        Int.MAX_VALUE
                    } else {
                        2
                    },
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}