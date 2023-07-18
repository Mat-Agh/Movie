package app.mat.movie.presentation.component.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.mat.movie.common.type.SortType
import app.mat.movie.presentation.component.dropDown.SortTypeDropDown

@Composable
fun SortTypeDropDownButton(
    modifier: Modifier = Modifier,
    selectedType: SortType,
    onTypeSelected: (SortType) -> Unit = {}
) {
    var showSortTypeDropDown by remember {
        mutableStateOf(
            false
        )
    }

    Box(
        modifier = modifier.wrapContentSize(
            Alignment.TopEnd
        )
    ) {
        IconButton(
            onClick = {
                showSortTypeDropDown = true
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Sort,
                contentDescription = null
            )
        }

        SortTypeDropDown(
            expanded = showSortTypeDropDown,
            onDismissRequest = {
                showSortTypeDropDown = false
            },
            selectedSortType = selectedType,
            onSortTypeSelected = { type ->
                showSortTypeDropDown = false

                if (type != selectedType) {
                    onTypeSelected(
                        type
                    )
                }
            }
        )
    }
}