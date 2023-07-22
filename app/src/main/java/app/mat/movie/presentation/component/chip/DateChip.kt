package app.mat.movie.presentation.component.chip

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import app.mat.movie.R
import app.mat.movie.common.util.createDateDialog
import app.mat.movie.common.util.formatted
import app.mat.movie.presentation.theme.spacing
import java.util.Date

@Composable
fun DateChip(
    initialDate: Date?,
    modifier: Modifier = Modifier,
    minDate: Date? = null,
    maxDate: Date? = null,
    onDateChanged: (Date) -> Unit = {},
    onClearClicked: () -> Unit = {}
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .clickable {
                createDateDialog(
                    context = context,
                    initialDate = initialDate,
                    onDateSelected = onDateChanged,
                    minDate = minDate,
                    maxDate = maxDate
                ).show()
            }
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = MaterialTheme.shapes.small
            )
            .padding(
                MaterialTheme.spacing.small
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.EditCalendar,
                contentDescription = null,
            )
            Spacer(
                modifier = Modifier.width(
                    MaterialTheme.spacing.small
                )
            )
            Text(
                text = initialDate?.formatted()
                    ?: stringResource(
                        R.string.date_range_selector_select_hint
                    ),
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(
                modifier = Modifier.weight(
                    1f
                )
            )
            AnimatedVisibility(
                visible = initialDate != null,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                Icon(
                    modifier = Modifier
                        .clip(
                            CircleShape
                        )
                        .clickable {
                            onClearClicked()
                        },
                    imageVector = Icons.Filled.Clear,
                    contentDescription = null
                )
            }
        }
    }
}