package app.mat.movie.presentation.component.chip

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString

@Composable
fun GenreChip(
    text: String
) {
    SuggestionChip(
        onClick = {},
        label = {
            Text(
                AnnotatedString(
                    text = text
                ),
                style = MaterialTheme.typography.labelSmall
            )
        },
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        border = null
    )
}