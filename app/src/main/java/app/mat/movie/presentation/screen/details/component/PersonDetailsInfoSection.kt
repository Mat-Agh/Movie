package app.mat.movie.presentation.screen.details.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import app.mat.movie.data.remote.dto.common.PersonDetailsDto
import app.mat.movie.presentation.component.text.ExpandableText
import app.mat.movie.presentation.theme.spacing

@Composable
fun PersonDetailsInfoSection(
    personDetails: PersonDetailsDto?,
    modifier: Modifier = Modifier
) {
    Crossfade(
        modifier = modifier,
        targetState = personDetails,
        label = ""
    ) { details ->
        if (details != null) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(
                    MaterialTheme.spacing.small
                )
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = MaterialTheme.spacing.medium
                        ),
                    text = details.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                if (details.biography.isNotEmpty()) {
                    ExpandableText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = MaterialTheme.spacing.medium
                            ),
                        text = details.biography
                    )
                }
            }
        }
    }
}