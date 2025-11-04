package app.sunreef.yachts.mobile.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Sunreef Yachts Theme
 * Custom theme for yacht automation app
 */

// Sunreef Yachts Color Palette
object SunreefYachtsColors {
    val PrimaryBlue = Color(0xFF0066CC)
    val SecondaryBlue = Color(0xFF00A0FF)
    val AccentOrange = Color(0xFFFF6B00)
    val SuccessGreen = Color(0xFF00CC66)
    val WarningYellow = Color(0xFFFFCC00)
    val ErrorRed = Color(0xFFFF3333)
    val NeutralGray = Color(0xFF666666)
    val BackgroundDark = Color(0xFF1A1A1A)
    val BackgroundLight = Color(0xFFFFFFFF)
}

private val LightColorScheme = lightColorScheme(
    primary = SunreefYachtsColors.PrimaryBlue,
    secondary = SunreefYachtsColors.SecondaryBlue,
    tertiary = SunreefYachtsColors.AccentOrange,
    background = SunreefYachtsColors.BackgroundLight,
    surface = Color(0xFFF5F5F5),
    error = SunreefYachtsColors.ErrorRed
)

private val DarkColorScheme = darkColorScheme(
    primary = SunreefYachtsColors.SecondaryBlue,
    secondary = SunreefYachtsColors.PrimaryBlue,
    tertiary = SunreefYachtsColors.AccentOrange,
    background = SunreefYachtsColors.BackgroundDark,
    surface = Color(0xFF2A2A2A),
    error = SunreefYachtsColors.ErrorRed
)

@Composable
fun SunreefYachtsTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

