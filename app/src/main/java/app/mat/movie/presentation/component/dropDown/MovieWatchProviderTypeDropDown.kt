package app.mat.movie.presentation.component.dropDown

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import app.mat.movie.common.type.MovieWatchProviderType

@Composable
fun MovieWatchProviderTypeDropDown(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    availableTypes: List<MovieWatchProviderType>,
    selectedType: MovieWatchProviderType,
    onDismissRequest: () -> Unit = {},
    onTypeSelected: (MovieWatchProviderType) -> Unit = {}
) {
    val items = availableTypes.map { type ->
        type to type.getLabelResId()
    }

    if (items.isNotEmpty()) {
        DropdownMenu(
            modifier = modifier,
            expanded = expanded,
            onDismissRequest = onDismissRequest
        ) {
            items.forEach { (type, labelResId) ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(
                                id = labelResId
                            ),
                            color = if (type == selectedType) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }, onClick = {
                        onTypeSelected(
                            type
                        )
                    }
                )
            }
        }
    }
}