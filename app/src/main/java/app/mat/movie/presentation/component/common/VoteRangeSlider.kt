package app.mat.movie.presentation.component.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import app.mat.movie.common.util.singleDecimalPlaceFormatted
import app.mat.movie.data.remote.dto.common.VoteRange

@Composable
fun VoteRangeSlider(
    voteRange: VoteRange,
    modifier: Modifier = Modifier,
    onCurrentVoteRangeChange: (ClosedFloatingPointRange<Float>) -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = voteRange.current.start.singleDecimalPlaceFormatted(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = voteRange.current.endInclusive.singleDecimalPlaceFormatted(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        RangeSlider(
            modifier = Modifier.fillMaxWidth(),
            value = voteRange.current,
            valueRange = voteRange.default,
            onValueChange = onCurrentVoteRangeChange
        )
    }
}