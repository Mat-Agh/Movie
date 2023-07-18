package app.mat.movie.presentation.component.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.mat.movie.data.remote.dto.common.VideoDto
import app.mat.movie.presentation.component.item.VideoItem
import app.mat.movie.presentation.component.text.SectionLabel
import app.mat.movie.presentation.theme.spacing

@Composable
fun VideosSection(
    videos: List<VideoDto>,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    title: String? = null,
    onVideoClicked: (VideoDto) -> Unit = {}
) {
    Column(modifier = modifier) {
        title?.let { title ->
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
                MaterialTheme.spacing.medium
            ),
            contentPadding = contentPadding
        ) {
            items(
                count = videos.size
            ) { index ->
                val video = videos[index]
                VideoItem(
                    video = video,
                    onVideoClick = {
                        onVideoClicked(
                            video
                        )
                    }
                )
            }
        }
    }
}