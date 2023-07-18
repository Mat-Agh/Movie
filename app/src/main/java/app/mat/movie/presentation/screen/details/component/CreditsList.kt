package app.mat.movie.presentation.screen.details.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.mat.movie.data.remote.dto.common.CreditPresentable
import app.mat.movie.data.remote.type.MediaType
import app.mat.movie.presentation.component.text.SectionLabel
import app.mat.movie.presentation.theme.spacing

@Composable
fun MovplayCreditsList(
    title: String,
    credits: List<CreditPresentable>,
    modifier: Modifier = Modifier,
    onCreditsClick: (MediaType, Int) -> Unit = { _, _ -> }
) {
    val creditsGroups = credits.groupBy { credit -> credit.id }.toList()

    if (credits.isNotEmpty()) {
        Column(
            modifier = modifier
        ) {
            SectionLabel(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.spacing.medium
                    ),
                text = title
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = MaterialTheme.spacing.small
                    ),
                horizontalArrangement = Arrangement.spacedBy(
                    MaterialTheme.spacing.small
                ),
                contentPadding = PaddingValues(
                    horizontal = MaterialTheme.spacing.medium
                )
            ) {
                items(
                    creditsGroups.count()
                ) { index ->
                    val credit = creditsGroups[index]
                    val posterPath = credits.firstNotNullOfOrNull { member -> member.posterPath }
                    val mediaType = credits.firstOrNull()?.mediaType
                    val mediaTitle = credits.firstNotNullOfOrNull { member -> member.title }
                    val infoText = credits.mapNotNull { member ->
                        member.infoText
                    }.joinToString(
                        separator = ", "
                    )

                    CreditsItem(
                        posterPath = posterPath,
                        infoText = infoText,
                        onClick = {
                            mediaType?.let { type ->
                                onCreditsClick(
                                    type,
                                    index
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}