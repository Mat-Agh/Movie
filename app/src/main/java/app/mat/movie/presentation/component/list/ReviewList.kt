package app.mat.movie.presentation.component.list

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import app.mat.movie.common.util.hasItems
import app.mat.movie.data.remote.dto.common.ReviewDto
import app.mat.movie.presentation.theme.spacing

@Composable
fun ReviewsList(
    reviews: LazyPagingItems<ReviewDto>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    arrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(
        MaterialTheme.spacing.large
    )
) {
    Crossfade(
        modifier = modifier,
        targetState = reviews.hasItems(),
        label = ""
    ) { hasReviews ->
        if (hasReviews) {
            ReviewsListResult(
                modifier = Modifier.fillMaxSize(),
                reviews = reviews,
                contentPadding = contentPadding,
                arrangement = arrangement
            )
        } else {
            ReviewsListPlaceholder(
                modifier = Modifier.fillMaxSize(),
                contentPadding = contentPadding,
                arrangement = arrangement
            )
        }
    }
}