package app.mat.movie.presentation.component.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.mat.movie.common.type.PresentableItemState
import app.mat.movie.data.remote.dto.common.SeasonDto
import app.mat.movie.presentation.component.item.PresentableItem
import app.mat.movie.presentation.component.text.SectionLabel
import app.mat.movie.presentation.theme.spacing

@Composable
fun SeasonSection(
    title: String,
    seasons: List<SeasonDto>,
    modifier: Modifier = Modifier,
    onSeasonClick: (Int) -> Unit = {}
) {
    val state = rememberLazyListState()
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MaterialTheme.spacing.medium
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SectionLabel(
                modifier = Modifier.weight(
                    1f
                ),
                text = title
            )
        }
        Box {
            LazyRow(
                state = state,
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
                    count = seasons.size
                ) { index ->
                    val season = seasons[index]
                    PresentableItem(
                        presentableState = PresentableItemState.Result(
                            season
                        ),
                        onClick = {
                            onSeasonClick(
                                season.seasonNumber
                            )
                        }
                    )
                }
            }
        }
    }
}