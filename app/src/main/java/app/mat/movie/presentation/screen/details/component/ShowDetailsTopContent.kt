package app.mat.movie.presentation.screen.details.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import app.mat.movie.R
import app.mat.movie.data.remote.dto.show.ShowDetailsDto
import app.mat.movie.presentation.component.text.LabeledText
import app.mat.movie.presentation.theme.spacing

@Composable
fun MovplayTvShowDetailsTopContent(
    tvShowDetails: ShowDetailsDto?,
    modifier: Modifier = Modifier
) {
    Crossfade(
        modifier = modifier,
        targetState = tvShowDetails,
        label = ""
    ) { details ->
        if (details != null) {
            Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)) {
                tvShowDetails?.let { details ->
                    LabeledText(
                        label = stringResource(
                            R.string.tv_series_details_type
                        ),
                        text = stringResource(
                            details.type.getLabel()
                        )
                    )
                    LabeledText(
                        label = stringResource(
                            R.string.tv_series_details_status
                        ),
                        text = stringResource(
                            details.status.getLabel()
                        )
                    )

                    LabeledText(
                        label = stringResource(
                            R.string.tv_series_details_in_production
                        ),
                        text = stringResource(
                            if (details.inProduction) {
                                R.string.yes
                            } else {
                                R.string.no
                            }
                        )
                    )
                }
            }
        }
    }
}