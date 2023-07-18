package app.mat.movie.presentation.component.selector

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import app.mat.movie.R
import app.mat.movie.presentation.component.chip.DateChip
import app.mat.movie.presentation.theme.spacing
import java.util.Date

@Composable
fun DateRangeSelector(
    fromDate: Date?,
    toDate: Date?,
    modifier: Modifier = Modifier,
    onFromDateChanged: (Date) -> Unit = {},
    onToDateChanged: (Date) -> Unit = {},
    onFromDateClearClicked: () -> Unit = {},
    onToDateClearClicked: () -> Unit = {}
) {
    val spacing = MaterialTheme.spacing.medium

    ConstraintLayout(
        modifier = modifier
    ) {
        val (fromLabel, fromDateChip, arrowIcon, toLabel, toDateChip) = createRefs()
        Icon(
            modifier = Modifier.constrainAs(
                arrowIcon
            ) {
                start.linkTo(
                    parent.start
                )
                end.linkTo(
                    parent.end
                )
                top.linkTo(
                    fromDateChip.top
                )
                bottom.linkTo(
                    fromDateChip.bottom
                )
            },
            imageVector = Icons.Filled.ArrowForward,
            contentDescription = null,
        )

        DateChip(
            modifier = Modifier
                .constrainAs(
                    fromDateChip
                ) {
                    top.linkTo(
                        fromLabel.bottom, margin = 4.dp
                    )
                    linkTo(
                        start = parent.start,
                        end = arrowIcon.start,
                        bias = 0f,
                        endMargin = spacing
                    )
                    width = Dimension.fillToConstraints
                },
            initialDate = fromDate,
            maxDate = toDate,
            onDateChanged = onFromDateChanged,
            onClearClicked = onFromDateClearClicked
        )

        DateChip(
            modifier = Modifier
                .constrainAs(
                    toDateChip
                ) {
                    top.linkTo(
                        toLabel.bottom,
                        margin = 4.dp
                    )
                    linkTo(
                        start = arrowIcon.end,
                        end = parent.end,
                        bias = 1f,
                        startMargin = spacing
                    )
                    width = Dimension.fillToConstraints
                },
            initialDate = toDate,
            minDate = fromDate,
            onDateChanged = onToDateChanged,
            onClearClicked = onToDateClearClicked
        )

        Text(
            modifier = Modifier
                .constrainAs(
                    fromLabel
                ) {
                    top.linkTo(
                        parent.top
                    )
                    start.linkTo(
                        fromDateChip.start
                    )
                    bottom.linkTo(
                        fromDateChip.top
                    )
                },
            text = stringResource(
                R.string.date_range_selector_from_label
            ),
            fontWeight = FontWeight.SemiBold
        )

        Text(
            modifier = Modifier
                .constrainAs(
                    toLabel
                ) {
                    top.linkTo(
                        parent.top
                    )
                    start.linkTo(
                        toDateChip.start
                    )
                    bottom.linkTo(
                        toDateChip.top
                    )
                },
            text = stringResource(
                R.string.date_range_selector_to_label
            ),
            fontWeight = FontWeight.SemiBold
        )
    }
}

