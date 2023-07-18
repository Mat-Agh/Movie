package app.mat.movie.presentation.component.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import app.mat.movie.data.remote.dto.common.ReviewDto
import app.mat.movie.presentation.component.item.ReviewItem
import app.mat.movie.presentation.theme.spacing

@Composable
fun ReviewsListResult(
    reviews: LazyPagingItems<ReviewDto>,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(),
    arrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(
        MaterialTheme.spacing.large
    )
) {
    LazyColumn(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        verticalArrangement = arrangement
    ) {
        items(
            count = reviews.itemCount
        ) { index ->
            val review = reviews[index]

            if (review != null) {
                val alignment = if (index % 2 == 0) {
                    Alignment.CenterStart
                } else {
                    Alignment.CenterEnd
                }

                val shape = if (index % 2 == 0) {
                    MaterialTheme.shapes.medium
                } else {
                    MaterialTheme.shapes.medium
                }

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ReviewItem(
                        modifier = Modifier
                            .fillMaxWidth(
                                0.9f
                            )
                            .align(
                                alignment
                            ),
                        review = review,
                        shape = shape
                    )
                }
            }
        }
    }
}