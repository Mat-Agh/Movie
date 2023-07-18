package app.mat.movie.presentation.component.common

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.mat.movie.common.util.defaultPlaceholder

@Composable
fun PosterPlaceHolder(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.defaultPlaceholder()
    )
}