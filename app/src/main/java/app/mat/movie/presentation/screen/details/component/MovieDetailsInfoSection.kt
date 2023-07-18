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
import androidx.compose.ui.unit.sp
import app.mat.movie.R
import app.mat.movie.common.util.formattedRuntime
import app.mat.movie.common.util.timeString
import app.mat.movie.common.util.yearString
import app.mat.movie.data.remote.dto.common.ShareDetailsModel
import app.mat.movie.data.remote.dto.movie.MovieDetailsDto
import app.mat.movie.data.remote.type.ExternalIdsResource
import app.mat.movie.presentation.component.section.GenresSection
import app.mat.movie.presentation.component.text.AdditionalInfoText
import app.mat.movie.presentation.component.text.ExpandableText
import app.mat.movie.presentation.theme.spacing
import java.util.*

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun MovplayMovieDetailsInfoSection(
    movieDetails: MovieDetailsDto?,
    watchAtTime: Date?,
    modifier: Modifier = Modifier,
    imdbExternalId: ExternalIdsResource.Imdb? = null,
    onShareClicked: (ShareDetailsModel) -> Unit = {}
) {
    val otherOriginalTitle: Boolean by derivedStateOf {
        movieDetails?.run { originalTitle.isNotEmpty() && title != originalTitle } ?: false
    }
    val watchAtTimeString = watchAtTime?.let { time ->
        stringResource(
            R.string.movie_details_watch_at,
            time.timeString()
        )
    }
    Crossfade(
        modifier = modifier,
        targetState = movieDetails,
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
                            text = details.title,
                            style = MaterialTheme.typography.headlineSmall,
                        )
                        if (otherOriginalTitle) {
                            Text(
                                text = details.originalTitle
                            )
                        }
                        AdditionalInfoText(
                            modifier = Modifier.fillMaxWidth(),
                            infoTexts = details.run {
                                listOfNotNull(
                                    releaseDate?.yearString(),
                                    runtime?.formattedRuntime(),
                                    watchAtTimeString
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
                    details.tagline?.let { tagline ->
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