package app.mat.movie.presentation.component.section

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import app.mat.movie.common.type.PresentableItemState
import app.mat.movie.common.util.isScrollingTowardsStart
import app.mat.movie.data.remote.dto.common.SearchResultDto
import app.mat.movie.data.remote.type.MediaType
import app.mat.movie.presentation.component.button.ScrollToTopButton
import app.mat.movie.presentation.component.item.PresentableItem
import app.mat.movie.presentation.theme.spacing
import kotlinx.coroutines.launch

@SuppressLint(
    "UnrememberedMutableState"
)
@Composable
fun SearchGridSection(
    state: LazyPagingItems<SearchResultDto>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(
        MaterialTheme.spacing.default
    ),
    scrollToBeginningItemsStart: Int = 30,
    onSearchResultClick: (Int, MediaType) -> Unit = { _, _ -> }
) {
    val coroutineScope = rememberCoroutineScope()
    val gridState = rememberLazyGridState()
    val isScrollingLeft = gridState.isScrollingTowardsStart()

    val showScrollToBeginningButton by derivedStateOf {
        val visibleMaxItem = gridState.firstVisibleItemIndex > scrollToBeginningItemsStart

        visibleMaxItem && isScrollingLeft
    }

    val onScrollToStartClick: () -> Unit = {
        coroutineScope.launch {
            gridState.animateScrollToItem(
                0
            )
        }
    }

    Box(modifier = modifier) {
        LazyVerticalGrid(
            state = gridState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding,
            columns = GridCells.Fixed(
                3
            ),
            verticalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.small
            ),
            horizontalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.small
            )
        ) {
            items(
                count = state.itemCount
            ) { index ->
                val presentable = state[index]
                presentable?.let { searchResult ->
                    PresentableItem(
                        presentableState = PresentableItemState.Result(
                            searchResult
                        ),
                        onClick = {
                            onSearchResultClick(
                                searchResult.id,
                                searchResult.mediaType
                            )
                        }
                    )
                }
            }
            state.apply {
                when (loadState.append) {
                    is LoadState.Loading -> {
                        items(
                            3
                        ) {
                            PresentableItem(
                                presentableState = PresentableItemState.Loading
                            )
                        }
                    }

                    else -> Unit
                }
            }
        }
        AnimatedVisibility(
            modifier = Modifier
                .align(
                    Alignment.TopEnd
                )
                .padding(
                    end = MaterialTheme.spacing.small,
                    top = MaterialTheme.spacing.small
                ),
            visible = showScrollToBeginningButton,
            enter = slideIn(
                animationSpec = tween(),
                initialOffset = { size ->
                    IntOffset(
                        size.width,
                        0
                    )
                }),
            exit = fadeOut(
                animationSpec = spring()
            ) + scaleOut(
                animationSpec = spring(),
                targetScale = 0.3f
            ),
        ) {
            ScrollToTopButton(
                modifier = modifier,
                onClick = onScrollToStartClick
            )
        }
    }
}