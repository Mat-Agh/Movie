package app.mat.movie.presentation.screen.details.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.mat.movie.common.type.ImageType
import app.mat.movie.common.util.TmdbImage
import app.mat.movie.presentation.component.item.ErrorPresentableItem
import app.mat.movie.presentation.component.item.LoadingPresentableItem
import app.mat.movie.presentation.component.item.NoPhotoPresentableItem
import app.mat.movie.presentation.screen.details.person.PersonDetailsState
import app.mat.movie.presentation.theme.ComposableSize
import app.mat.movie.presentation.theme.sizes
import coil.size.Scale
import coil.size.Size

@Composable
fun MovplayPersonProfileImage(
    personDetailsState: PersonDetailsState,
    modifier: Modifier = Modifier,
    size: ComposableSize = MaterialTheme.sizes.presentableItemBig
) {
    Card(
        modifier = modifier
            .width(
                size.width
            )
            .height(
                size.height
            ),
        shape = MaterialTheme.shapes.medium
    ) {
        when (personDetailsState) {
            is PersonDetailsState.Loading -> {
                LoadingPresentableItem(
                    modifier = Modifier.fillMaxSize()
                )
            }

            is PersonDetailsState.Error -> {
                ErrorPresentableItem(
                    modifier = Modifier.fillMaxSize()
                )
            }

            is PersonDetailsState.Result -> {
                val profilePath = personDetailsState.details.profilePath

                if (profilePath != null) {
                    TmdbImage(
                        modifier = Modifier.fillMaxSize(),
                        imagePath = profilePath,
                        imageType = ImageType.Profile
                    ) {
                        size(
                            Size.ORIGINAL
                        )
                        scale(
                            Scale.FILL
                        )
                        crossfade(
                            true
                        )
                    }
                } else {
                    NoPhotoPresentableItem(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}