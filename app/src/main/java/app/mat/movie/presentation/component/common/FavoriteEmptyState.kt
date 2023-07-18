package app.mat.movie.presentation.component.common

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.mat.movie.R
import app.mat.movie.common.type.FavoriteType
import app.mat.movie.presentation.theme.spacing
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty

@Composable
fun FavoriteEmptyState(
    type: FavoriteType,
    modifier: Modifier,
    onButtonClick: () -> Unit = {}
) {
    @StringRes
    val infoTextResId = when (type) {
        FavoriteType.Movie -> R.string.favourite_empty_movies_info_text
        FavoriteType.TvShow -> R.string.favourite_empty_tv_series_info_text
    }

    @StringRes
    val buttonLabelResId = when (type) {
        FavoriteType.Movie -> R.string.favourite_empty_navigate_to_movies_button_label
        FavoriteType.TvShow -> R.string.favourite_empty_navigate_to_tv_series_button_label
    }

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.lottie_empty
        )
    )
    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR_FILTER,
            value = PorterDuffColorFilter(
                MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.7f
                ).toArgb(),
                PorterDuff.Mode.SRC_ATOP
            ),
            keyPath = arrayOf(
                "**"
            )
        )
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimation(
            modifier = Modifier.size(
                200.dp
            ),
            composition = composition,
            speed = 0.5f,
            iterations = LottieConstants.IterateForever,
            dynamicProperties = dynamicProperties
        )
        Spacer(
            modifier = Modifier.height(
                MaterialTheme.spacing.medium
            )
        )
        Text(
            text = stringResource(
                infoTextResId
            ),
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(
            modifier = Modifier.height(
                MaterialTheme.spacing.medium
            )
        )
        TextButton(
            onClick = onButtonClick
        ) {
            Text(
                modifier = Modifier.animateContentSize(),
                text = stringResource(buttonLabelResId),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}