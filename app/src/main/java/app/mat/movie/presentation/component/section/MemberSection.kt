package app.mat.movie.presentation.component.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.mat.movie.data.remote.dto.common.MemberDto
import app.mat.movie.presentation.component.chip.MemberResultChip
import app.mat.movie.presentation.component.text.SectionLabel
import app.mat.movie.presentation.theme.spacing

@Composable
fun MemberSection(
    members: List<MemberDto>,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    title: String? = null,
    onMemberClick: (Int) -> Unit = {}
) {
    val memberGroups = members.groupBy { member -> member.id }.toList()

    Column(
        modifier = modifier
    ) {
        title?.let {
            SectionLabel(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = MaterialTheme.spacing.medium
                    ),
                text = title
            )
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = MaterialTheme.spacing.small
                ),
            horizontalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.small
            ),
            contentPadding = contentPadding
        ) {
            items(
                count = memberGroups.count()
            ) { id ->
                val profilePath = members.firstNotNullOfOrNull { member -> member.profilePath }
                val firstLine = members.firstNotNullOfOrNull { member -> member.firstLine }
                val secondLine = members.mapNotNull { member -> member.secondLine }
                    .joinToString(
                        separator = ", "
                    )

                MemberResultChip(
                    modifier = Modifier.width(
                        72.dp
                    ),
                    profilePath = profilePath,
                    firstLine = firstLine,
                    secondLine = secondLine,
                    onClick = {
                        onMemberClick(
                            id
                        )
                    }
                )
            }
        }
    }
}