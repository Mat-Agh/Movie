package app.mat.movie.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

//region Colors
val Green10 = Color(0xff003314)
val Green20 = Color(0xff006627)
val Green30 = Color(0xff00993b)
val Green40 = Color(0xff00cc4e)
val Green80 = Color(0xff99ffc0)
val Green90 = Color(0xffccffe0)

val Orange10 = Color(0xffbf360c)
val Orange20 = Color(0xffd84315)
val Orange30 = Color(0xffe64a19)
val Orange40 = Color(0xfff4511e)
val Orange80 = Color(0xffff8a65)
val Orange90 = Color(0xffffab91)

val DarkGreen10 = Color(0xff0d260d)
val DarkGreen20 = Color(0xff194d19)
val DarkGreen30 = Color(0xff267326)
val DarkGreen40 = Color(0xff339933)
val DarkGreen80 = Color(0xffb3e6b3)
val DarkGreen90 = Color(0xffd9f2d9)

val Violet10 = Color(0xff330033)
val Violet20 = Color(0xff660066)
val Violet30 = Color(0xff990099)
val Violet40 = Color(0xffcc00cc)
val Violet80 = Color(0xffff99ff)
val Violet90 = Color(0xffffccff)

val Red10 = Color(0xFF410001)
val Red20 = Color(0xFF680003)
val Red30 = Color(0xFF930006)
val Red40 = Color(0xFFBA1B1B)
val Red80 = Color(0xFFFFB4A9)
val Red90 = Color(0xFFFFDAD4)

val Grey10 = Color(0xFF191C1D)
val Grey20 = Color(0xFF2D3132)
val Grey90 = Color(0xFFE0E3E3)
val Grey95 = Color(0xFFEFF1F1)
val Grey99 = Color(0xFFFBFDFD)

val GreenGrey30 = Color(0xFF316847)
val GreenGrey50 = Color(0xFF52ad76)
val GreenGrey60 = Color(0xFF74be92)
val GreenGrey80 = Color(0xFFbadec8)
val GreenGrey90 = Color(0xFFdcefe4)

val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
//endregion Colors

//region Palettes
val DarkColorPalette = darkColorScheme(
    primary = Green80,
    onPrimary = Green20,
    primaryContainer = Green30,
    onPrimaryContainer = Green90,
    inversePrimary = Green40,
    secondary = DarkGreen80,
    onSecondary = DarkGreen20,
    secondaryContainer = DarkGreen30,
    onSecondaryContainer = DarkGreen90,
    tertiary = Violet80,
    onTertiary = Violet20,
    tertiaryContainer = Violet30,
    onTertiaryContainer = Violet90,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = Grey10,
    onBackground = Grey90,
    surface = GreenGrey30,
    onSurface = GreenGrey80,
    inverseSurface = Grey90,
    inverseOnSurface = Grey10,
    surfaceVariant = GreenGrey30,
    onSurfaceVariant = GreenGrey80,
    outline = GreenGrey80
)

val LightColorPalette = lightColorScheme(
    primary = Green40,
    onPrimary = Color.White,
    primaryContainer = Green90,
    onPrimaryContainer = Green10,
    inversePrimary = Green80,
    secondary = DarkGreen40,
    onSecondary = Color.White,
    secondaryContainer = DarkGreen90,
    onSecondaryContainer = DarkGreen10,
    tertiary = Violet40,
    onTertiary = Color.White,
    tertiaryContainer = Violet90,
    onTertiaryContainer = Violet10,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = Grey99,
    onBackground = Grey10,
    surface = GreenGrey90,
    onSurface = GreenGrey30,
    inverseSurface = Grey20,
    inverseOnSurface = Grey95,
    surfaceVariant = GreenGrey90,
    onSurfaceVariant = GreenGrey30,
    outline = GreenGrey50
)
//endreigon Palettes