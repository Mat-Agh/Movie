package app.mat.movie.presentation.component.button

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import app.mat.movie.R
import app.mat.movie.presentation.theme.spacing

@Composable
fun BackButton(
    modifier: Modifier,
    onBackClicked: () -> Unit = {}
) {
    Button(
        modifier = modifier.padding(
            start = MaterialTheme.spacing.extraSmall
        ),
        onClick = onBackClicked,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(
                id = R.string.back
            )
        )
    }
}