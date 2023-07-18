package app.mat.movie.presentation.component.dropDown

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import app.mat.movie.common.type.SortType

@Composable
fun SortTypeDropDown(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    selectedSortType: SortType,
    onDismissRequest: () -> Unit = {},
    onSortTypeSelected: (SortType) -> Unit = {}
) {
    val items = SortType.values().map { type ->
        type to type.getLabelResId()
    }

    DropdownMenu(
        modifier = modifier,
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        items.forEach { (type, labelResId) ->
            DropdownMenuItem(
                content = {
                    Text(
                        text = stringResource(labelResId),
                        color = if (type == selectedSortType) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onBackground
                        }
                    )
                },
                onClick = {
                    onSortTypeSelected(
                        type
                    )
                }
            )
        }
    }
}
