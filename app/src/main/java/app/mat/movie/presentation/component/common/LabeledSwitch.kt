package app.mat.movie.presentation.component.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import app.mat.movie.presentation.theme.spacing

@Composable
fun LabeledSwitch(
    label: String,
    checked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChanged: (Boolean) -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(
                    1f
                )
                .padding(
                    end = MaterialTheme.spacing.medium
                ),
            text = label,
            fontSize = 14.sp
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChanged,
        )
    }
}