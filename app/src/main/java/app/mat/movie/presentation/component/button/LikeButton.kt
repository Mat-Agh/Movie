package app.mat.movie.presentation.component.button

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import app.mat.movie.R
import app.mat.movie.presentation.theme.spacing

@Composable
fun LikeButton(
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier.padding(
            end = MaterialTheme.spacing.extraSmall
        ),
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        AnimatedContent(
            targetState = isFavorite,
            contentAlignment = Alignment.Center,
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = 200
                    )
                ) + scaleIn(
                    animationSpec = tween(
                        durationMillis = 200,
                        delayMillis = 200
                    ),
                ) togetherWith scaleOut(
                    animationSpec = tween(
                        durationMillis = 200
                    ),
                    targetScale = 0.8f
                )
            }, label = ""
        ) { isFavorite ->
            if (isFavorite) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = stringResource(
                        id = R.string.add_to_favorite
                    ),
                    tint = MaterialTheme.colorScheme.secondary
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.FavoriteBorder,
                    contentDescription = stringResource(
                        id = R.string.remove_from_favorite
                    ),
                    tint = MaterialTheme.colorScheme.secondaryContainer
                )
            }
        }
    }
}