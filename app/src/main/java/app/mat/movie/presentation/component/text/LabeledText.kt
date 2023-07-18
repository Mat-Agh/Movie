package app.mat.movie.presentation.component.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.Dp
import app.mat.movie.presentation.theme.spacing

@Composable
fun LabeledText(
    label: String,
    text: String,
    modifier: Modifier = Modifier,
    spacing: Dp = MaterialTheme.spacing.default
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            spacing
        )
    ) {
        Text(
            text = AnnotatedString(
                text = label
            ),
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = AnnotatedString(
                text = text
            ),
            style = MaterialTheme.typography.labelSmall
        )
    }
}