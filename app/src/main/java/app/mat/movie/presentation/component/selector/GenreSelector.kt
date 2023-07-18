package app.mat.movie.presentation.component.selector

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.mat.movie.data.remote.dto.common.GenreDto

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class
)
@Composable
fun GenresSelector(
    genres: List<GenreDto>,
    selectedGenres: List<GenreDto>,
    modifier: Modifier = Modifier,
    onGenreClick: (GenreDto) -> Unit = {}
) {
    androidx.compose.foundation.layout.FlowRow(
        modifier = modifier,
    ) {
        genres.sortedBy { genre ->
            genre.name
        }.map { genre ->
            FilterChip(
                modifier = Modifier.animateContentSize(),
                selected = genre in selectedGenres,
                onClick = {
                    onGenreClick(
                        genre
                    )
                },
                label = {
                    Text(
                        genre.name
                    )
                },
                leadingIcon = if (genre in selectedGenres) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Localized Description",
                            modifier = Modifier.size(
                                FilterChipDefaults.IconSize
                            )
                        )
                    }
                } else {
                    null
                }
            )
        }
    }
}