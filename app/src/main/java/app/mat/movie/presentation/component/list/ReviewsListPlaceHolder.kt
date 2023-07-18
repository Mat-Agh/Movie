package app.mat.movie.presentation.component.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.mat.movie.presentation.component.item.ReviewItemPlaceholder
import app.mat.movie.presentation.theme.spacing

@Composable
fun ReviewsListPlaceholder(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    arrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(
        MaterialTheme.spacing.large
    )
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = arrangement
    ) {
        items(
            10
        ) { index ->
            val height: Int = rememberSaveable(
                index
            ) {
                (150..200).random()
            }

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
                ReviewItemPlaceholder(
                    modifier = Modifier
                        .fillMaxWidth(
                            0.9f
                        )
                        .height(
                            height.dp
                        )
                        .align(
                            alignment
                        ),
                    shape = shape
                )
            }
        }
    }
}