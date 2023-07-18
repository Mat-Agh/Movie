package app.mat.movie.presentation.screen.details.component

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import app.mat.movie.R
import app.mat.movie.common.util.yearRangeString
import app.mat.movie.data.remote.dto.common.ShareDetailsModel
import app.mat.movie.data.remote.dto.show.ShowDetailsDto
import app.mat.movie.data.remote.type.ExternalIdsResource
import app.mat.movie.presentation.component.section.GenresSection
import app.mat.movie.presentation.component.text.AdditionalInfoText
import app.mat.movie.presentation.component.text.ExpandableText
import app.mat.movie.presentation.theme.spacing

@SuppressLint(
    "UnrememberedMutableState"
)
@Composable
fun MovplayTvShowDetailsInfoSection(
    tvShowDetails: ShowDetailsDto?,
    nextEpisodeDaysRemaining: Long?,
    modifier: Modifier = Modifier,
    imdbExternalId: ExternalIdsResource.Imdb? = null,
    onShareClicked: (ShareDetailsModel) -> Unit = {}
) {
    val otherOriginalTitle: Boolean by derivedStateOf {
        tvShowDetails?.run { !originalName.isNullOrEmpty() && title != originalName } ?: false
    }

    Crossfade(
        modifier = modifier,
        targetState = tvShowDetails,
        label = ""
    ) { details ->
        if (details != null) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(
                    MaterialTheme.spacing.small
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(
                            1f
                        )
                    ) {
                        Text(
                            text = details.name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold
                        )
                        details.originalName?.let { name ->
                            if (otherOriginalTitle) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = name
                                )
                            }
                        }
                        AdditionalInfoText(
                            modifier = Modifier.fillMaxWidth(),
                            infoTexts = details.run {
                                listOfNotNull(
                                    yearRangeString(
                                        from = firstAirDate,
                                        to = lastAirDate
                                    ),
                                    nextEpisodeDaysRemaining?.let { days ->
                                        when (days) {
                                            0L -> stringResource(
                                                R.string.next_episode_today_text
                                            )

                                            1L -> stringResource(
                                                R.string.next_episode_tomorrow_text
                                            )

                                            else -> stringResource(
                                                R.string.next_episode_days_text,
                                                days
                                            )
                                        }
                                    }
                                )
                            }
                        )
                    }

                    AnimatedVisibility(
                        visible = imdbExternalId != null,
                        enter = fadeIn() + scaleIn(
                            initialScale = 0.7f
                        ),
                        exit = fadeOut() + scaleOut()
                    ) {
                        IconButton(
                            modifier = Modifier.background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = CircleShape
                            ),
                            onClick = {
                                imdbExternalId?.let { id ->
                                    val shareDetails = ShareDetailsModel(
                                        title = details.title,
                                        imdbId = id
                                    )

                                    onShareClicked(
                                        shareDetails
                                    )
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Share,
                                contentDescription = "share",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                if (details.genres.isNotEmpty()) {
                    GenresSection(
                        genres = details.genres
                    )
                }

                Column(
                    modifier = Modifier.padding(
                        top = MaterialTheme.spacing.small
                    ),
                    verticalArrangement = Arrangement.spacedBy(
                        MaterialTheme.spacing.extraSmall
                    )
                ) {
                    details.tagline.let { tagline ->
                        if (tagline.isNotEmpty()) {
                            Text(
                                text = "\"$tagline\"",
                                fontStyle = FontStyle.Italic,
                                fontSize = 12.sp
                            )
                        }
                    }

                    details.overView.let { overview ->
                        if (overview.isNotBlank()) {
                            ExpandableText(
                                modifier = Modifier.fillMaxWidth(),
                                text = overview
                            )
                        }
                    }
                }
            }
        }
    }
}