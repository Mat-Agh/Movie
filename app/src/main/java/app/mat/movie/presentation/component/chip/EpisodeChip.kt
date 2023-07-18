package app.mat.movie.presentation.component.chip

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import app.mat.movie.R
import app.mat.movie.common.util.formatted
import app.mat.movie.data.remote.dto.common.EpisodeDto
import app.mat.movie.data.remote.dto.common.ImageDto
import app.mat.movie.presentation.component.common.StillBrowser
import app.mat.movie.presentation.theme.spacing

@SuppressLint(
    "UnrememberedMutableState"
)
@Composable
fun EpisodeChip(
    modifier: Modifier = Modifier,
    episode: EpisodeDto,
    expanded: Boolean = false,
    enabled: Boolean = true,
    stills: List<ImageDto>? = null,
    onClick: () -> Unit = {}
) {
    val iconRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f, label = ""
    )

    val borderColor by animateColorAsState(
        targetValue = if (enabled) {
            MaterialTheme.colorScheme.primary
        } else MaterialTheme.colorScheme.surfaceVariant, label = ""
    )

    val hasAdditionalContent = derivedStateOf {
        episode.run {
            overview.isNotBlank() || stills == null || stills.isNotEmpty()
        }
    }

    Card(
        modifier = modifier.clickable(
            enabled = enabled,
            onClick = {
                onClick.invoke()
            }
        ),
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    MaterialTheme.spacing.medium
                )
                .animateContentSize(),
            verticalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.small
            )
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = modifier.weight(
                        1f
                    ),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = AnnotatedString(
                            text = episode.name
                        ),
                        style = MaterialTheme.typography.titleMedium,
                    )

                    episode.airDate?.let { date ->
                        Text(
                            text = AnnotatedString(
                                text = date.formatted()
                            ),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    if (episode.voteCount != 0) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(
                                MaterialTheme.spacing.small
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = stringResource(
                                        id = R.string.rating
                                    ),
                                    tint = Color.Yellow,
                                    modifier = modifier.size(
                                        16.dp
                                    )
                                )

                                Text(
                                    text = stringResource(
                                        id = R.string._rating,
                                        episode.voteAverage,
                                        10
                                    ),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }

                            Text(
                                text = stringResource(
                                    id = R.string.vote_count,
                                    episode.voteCount,
                                ),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
                Spacer(
                    modifier = modifier.width(
                        MaterialTheme.spacing.medium
                    )
                )

                AnimatedVisibility(
                    visible = enabled
                ) {
                    Icon(
                        modifier = Modifier.rotate(
                            iconRotation
                        ),
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = stringResource(
                            id = if (expanded) {
                                R.string.collapse
                            } else {
                                R.string.expand
                            }
                        )
                    )
                }

                AnimatedVisibility(
                    enter = fadeIn(),
                    exit = fadeOut(),
                    visible = expanded
                ) {
                    Crossfade(
                        modifier = modifier.fillMaxWidth(),
                        targetState = hasAdditionalContent,
                        label = "",
                    ) { hasAdditionalContent ->
                        if (hasAdditionalContent.value) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(
                                    MaterialTheme.spacing.medium
                                )
                            ) {
                                if (episode.overview.isNotBlank()) {
                                    Text(
                                        text = AnnotatedString(
                                            text = episode.overview
                                        ),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }

                                stills?.let { stills ->
                                    if (stills.isNotEmpty()) {
                                        StillBrowser(
                                            modifier = Modifier.fillMaxWidth(),
                                            stillPaths = stills
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}