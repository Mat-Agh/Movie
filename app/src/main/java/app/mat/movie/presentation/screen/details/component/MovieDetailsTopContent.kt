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
import app.mat.movie.common.util.formattedMoney
import app.mat.movie.data.remote.dto.movie.MovieDetailsDto
import app.mat.movie.presentation.component.text.LabeledText
import app.mat.movie.presentation.theme.spacing

@Composable
fun MovplayMovieDetailsTopContent(
    movieDetails: MovieDetailsDto?,
    modifier: Modifier = Modifier
) {
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
                LabeledText(
                    label = stringResource(
                        R.string.movie_details_status
                    ),
                    text = stringResource(
                        details.status.getLabel()
                    )
                )

                if (details.budget > 0) {
                    LabeledText(
                        label = stringResource(
                            R.string.movie_details_budget
                        ),
                        text = details.budget.formattedMoney()
                    )
                }
                if (details.revenue > 0) {
                    LabeledText(
                        label = stringResource(
                            R.string.movie_details_box_office
                        ),
                        text = details.revenue.formattedMoney()
                    )
                }
            }
        }
    }
}