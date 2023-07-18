package app.mat.movie.presentation.component.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import app.mat.movie.common.type.MovieWatchProviderType

@Composable
fun MoviesWatchProvidersTypeButton(
    availableTypes: List<MovieWatchProviderType>,
    selectedType: MovieWatchProviderType,
    modifier: Modifier = Modifier,
    onTypeSelected: (MovieWatchProviderType) -> Unit = {}
) {
    var showMovieWatchProviderTypeDropDown by remember {
        mutableStateOf(
            false
        )
    }

    Box(
        modifier = modifier.wrapContentSize(
            Alignment.TopEnd
        )
    ) {
        TextButton(
            onClick = {
                showMovieWatchProviderTypeDropDown = true
            }
        ) {
            Text(
                text = stringResource(
                    selectedType.getLabelResId()
                ),
                style = MaterialTheme.typography.labelMedium
            )
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = null
            )
        }
    }
}