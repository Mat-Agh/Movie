package app.mat.movie.presentation.component.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.mat.movie.presentation.theme.spacing

@Composable
fun BasicAppBar(
    title: String?,
    modifier: Modifier = Modifier,
    action: @Composable () -> Unit = {},
    trailing: @Composable () -> Unit = {}
) {
    Row(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.background
            )
            .fillMaxWidth()
            .statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    56.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            action()
            Spacer(
                modifier = Modifier.width(
                    MaterialTheme.spacing.small
                )
            )
            title?.let {
                Text(
                    modifier = Modifier
                        .weight(
                            1f
                        )
                        .padding(
                            end = MaterialTheme.spacing.medium
                        ),
                    text = it,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            trailing()
        }
    }
}