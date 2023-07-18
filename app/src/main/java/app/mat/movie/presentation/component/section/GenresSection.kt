package app.mat.movie.presentation.component.section

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.mat.movie.data.remote.dto.common.GenreDto
import app.mat.movie.presentation.component.chip.GenreChip

@OptIn(
    ExperimentalLayoutApi::class
)
@Composable
fun GenresSection(
    genres: List<GenreDto>,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier
    ) {
        genres.map { genre ->
            GenreChip(
                text = genre.name
            )
        }
    }
}