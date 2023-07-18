package app.mat.movie.presentation.screen.details.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import app.mat.movie.R
import app.mat.movie.common.util.formatted
import app.mat.movie.data.remote.dto.common.PersonDetailsDto
import app.mat.movie.presentation.component.text.LabeledText
import app.mat.movie.presentation.theme.spacing

@Composable
fun MovplayPersonDetailsTopContent(
    personDetails: PersonDetailsDto?,
    modifier: Modifier = Modifier
) {
    Crossfade(
        modifier = modifier,
        targetState = personDetails, label = ""
    ) { details ->
        if (details != null) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                LabeledText(
                    label = stringResource(
                        R.string.person_details_screen_know_for_label
                    ),
                    text = details.knownFor
                )

                details.birthPlace?.let { birthplace ->
                    LabeledText(
                        label = stringResource(
                            R.string.person_details_screen_birthplace
                        ),
                        text = birthplace
                    )
                }

                details.birthday?.let { date ->
                    LabeledText(
                        label = stringResource(
                            R.string.person_details_screen_birthday
                        ),
                        text = date.formatted()
                    )
                }

                details.deathDate?.let { date ->
                    LabeledText(
                        label = stringResource(
                            R.string.person_details_screen_death_date
                        ),
                        text = date.formatted()
                    )
                }
            }
        }
    }
}