package app.mat.movie.presentation.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import app.mat.movie.R

@Composable
fun ScrollToStartButton(
    modifier: Modifier,
    onClick: () -> Unit = {}
) {
    IconButton(
        modifier = modifier
            .clip(
                shape = CircleShape
            )
            .background(
                MaterialTheme.colorScheme.primaryContainer
            ),
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(
                id = R.string.scroll_to_start
            ),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}