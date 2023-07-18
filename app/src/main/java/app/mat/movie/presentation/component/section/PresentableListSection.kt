package app.mat.movie.presentation.component.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import app.mat.movie.common.type.PresentableItemState
import app.mat.movie.data.remote.dto.common.Presentable
import app.mat.movie.presentation.component.item.PresentableItem
import app.mat.movie.presentation.component.text.SectionLabel
import app.mat.movie.presentation.theme.spacing

@Composable
fun PresentableListSection(
    title: String,
    list: List<Presentable>,
    modifier: Modifier = Modifier,
    selectedId: Int? = null,
    onPresentableClick: (Int) -> Unit = {}
) {
    val lazyListState = rememberLazyListState()

    LaunchedEffect(selectedId) {
        val itemIndex = list.indexOfFirst { presentable -> presentable.id == selectedId }

        if (itemIndex >= 0) {
            lazyListState.scrollToItem(
                itemIndex
            )
        }
    }
    if (list.isNotEmpty()) {
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
                state = lazyListState,
                horizontalArrangement = Arrangement.spacedBy(
                    MaterialTheme.spacing.small
                ),
                contentPadding = PaddingValues(
                    horizontal = MaterialTheme.spacing.medium
                )
            ) {
                items(
                    count = list.count()
                ) { index ->
                    val presentable = list[index]
                    PresentableItem(
                        presentableState = PresentableItemState.Result(presentable),
                        selected = selectedId == presentable.id,
                        onClick = {
                            onPresentableClick(
                                presentable.id
                            )
                        }
                    )
                }
            }
        }
    }
}