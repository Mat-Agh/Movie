package app.mat.movie.presentation.component.common

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.mat.movie.R
import app.mat.movie.presentation.theme.spacing
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty

@Composable
fun FilterEmptyState(
    modifier: Modifier = Modifier,
    onFilterButtonClicked: () -> Unit = {}
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.lottie_search
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
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            modifier = Modifier.size(
                160.dp
            ),
            composition = composition,
            speed = 0.2f,
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
                R.string.filter_empty_info_text
            ),
        )
        Spacer(
            modifier = Modifier.height(
                MaterialTheme.spacing.medium
            )
        )
        OutlinedButton(
            onClick = onFilterButtonClicked
        ) {
            Text(
                text = stringResource(
                    R.string.filter_empty_button_change_filters_label
                )
            )
        }
    }
}