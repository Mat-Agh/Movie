package app.mat.movie.presentation.component.selector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.mat.movie.common.type.FavoriteType
import app.mat.movie.presentation.component.button.FavoriteTypeButton

@Composable
fun FavoriteTypeSelector(
    selected: FavoriteType,
    modifier: Modifier = Modifier,
    onSelected: (FavoriteType) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        FavoriteType.values().map { type ->
            FavoriteTypeButton(
                type = type,
                selected = type == selected,
                onClick = {
                    onSelected(
                        type
                    )
                }
            )
        }
    }
}