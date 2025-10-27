package app.shared.mobile.presentation.theme


import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import app.shared.mobile.resources.*
import kmp.core.mobile.presentation.theme.CoreTypography
import org.jetbrains.compose.resources.Font

// Define font families
@Composable
internal fun getAlexandriaFontFamily() = FontFamily(
    Font(Res.font.Alexandria_Black, FontWeight.Black),
    Font(Res.font.Alexandria_Bold, FontWeight.Bold),
    Font(Res.font.Alexandria_ExtraBold, FontWeight.ExtraBold),
    Font(Res.font.Alexandria_SemiBold, FontWeight.SemiBold),
    Font(Res.font.Alexandria_Medium, FontWeight.Medium),
    Font(Res.font.Alexandria_Regular, FontWeight.Normal),
    Font(Res.font.Alexandria_Light, FontWeight.Light),
    Font(Res.font.Alexandria_Thin, FontWeight.Thin)
)

// Create app typography
@Immutable
data class AppTypography(
    val primary: TextStyle = TextStyle(),
    val primaryBlack: TextStyle = primary.copy(
        fontWeight = FontWeight.Black
    ),
    val primaryBold: TextStyle = primary.copy(
        fontWeight = FontWeight.Bold
    ),
    val primaryMedium: TextStyle = primary.copy(
        fontWeight = FontWeight.Medium
    ),
    val primaryRegular: TextStyle = primary.copy(
        fontWeight = FontWeight.Normal
    ),
    val primaryLight: TextStyle = primary.copy(
        fontWeight = FontWeight.Light
    ),
    val primaryThin: TextStyle = primary.copy(
        fontWeight = FontWeight.Thin
    ),
    val primaryBlack12: TextStyle = primaryBlack.copy(
        fontSize = 12.sp
    ),
    val primaryBlack14: TextStyle = primaryBlack.copy(
        fontSize = 14.sp
    ),
    val primaryBlack16: TextStyle = primaryBlack.copy(
        fontSize = 16.sp
    ),
    val primaryBlack18: TextStyle = primaryBlack.copy(
        fontSize = 18.sp
    ),
    val primaryBlack20: TextStyle = primaryBlack.copy(
        fontSize = 20.sp
    ),
    val primaryBlack22: TextStyle = primaryBlack.copy(
        fontSize = 22.sp
    ),
    val primaryBlack24: TextStyle = primaryBlack.copy(
        fontSize = 24.sp
    ),
    val primaryBlack26: TextStyle = primaryBlack.copy(
        fontSize = 26.sp
    ),
    val primaryBlack28: TextStyle = primaryBlack.copy(
        fontSize = 28.sp
    ),
    val primaryBlack30: TextStyle = primaryBlack.copy(
        fontSize = 30.sp
    ),
    val primaryBlack32: TextStyle = primaryBlack.copy(
        fontSize = 32.sp
    ),
    val primaryBlack34: TextStyle = primaryBlack.copy(
        fontSize = 34.sp
    ),
    val primaryBlack36: TextStyle = primaryBlack.copy(
        fontSize = 36.sp
    ),
    val primaryBold12: TextStyle = primaryBold.copy(
        fontSize = 12.sp
    ),
    val primaryBold14: TextStyle = primaryBold.copy(
        fontSize = 14.sp
    ),
    val primaryBold16: TextStyle = primaryBold.copy(
        fontSize = 16.sp
    ),
    val primaryBold18: TextStyle = primaryBold.copy(
        fontSize = 18.sp
    ),
    val primaryBold20: TextStyle = primaryBold.copy(
        fontSize = 20.sp
    ),
    val primaryBold22: TextStyle = primaryBold.copy(
        fontSize = 22.sp
    ),
    val primaryBold24: TextStyle = primaryBold.copy(
        fontSize = 24.sp
    ),
    val primaryBold26: TextStyle = primaryBold.copy(
        fontSize = 26.sp
    ),
    val primaryBold28: TextStyle = primaryBold.copy(
        fontSize = 28.sp
    ),
    val primaryBold30: TextStyle = primaryBold.copy(
        fontSize = 30.sp
    ),
    val primaryBold32: TextStyle = primaryBold.copy(
        fontSize = 32.sp
    ),
    val primaryBold34: TextStyle = primaryBold.copy(
        fontSize = 34.sp
    ),
    val primaryBold36: TextStyle = primaryBold.copy(
        fontSize = 36.sp
    ),
    val primaryBold48: TextStyle = primaryBold.copy(
        fontSize = 48.sp
    ),
    val primaryMedium10: TextStyle = primaryMedium.copy(
        fontSize = 10.sp
    ),
    val primaryMedium12: TextStyle = primaryMedium.copy(
        fontSize = 12.sp
    ),
    val primaryMedium14: TextStyle = primaryMedium.copy(
        fontSize = 14.sp
    ),
    val primaryMedium16: TextStyle = primaryMedium.copy(
        fontSize = 16.sp
    ),
    val primaryMedium18: TextStyle = primaryMedium.copy(
        fontSize = 18.sp
    ),
    val primaryMedium20: TextStyle = primaryMedium.copy(
        fontSize = 20.sp
    ),
    val primaryMedium22: TextStyle = primaryMedium.copy(
        fontSize = 22.sp
    ),
    val primaryMedium24: TextStyle = primaryMedium.copy(
        fontSize = 24.sp
    ),
    val primaryMedium26: TextStyle = primaryMedium.copy(
        fontSize = 26.sp
    ),
    val primaryMedium28: TextStyle = primaryMedium.copy(
        fontSize = 28.sp
    ),
    val primaryMedium30: TextStyle = primaryMedium.copy(
        fontSize = 30.sp
    ),
    val primaryMedium32: TextStyle = primaryMedium.copy(
        fontSize = 32.sp
    ),
    val primaryMedium34: TextStyle = primaryMedium.copy(
        fontSize = 34.sp
    ),
    val primaryMedium36: TextStyle = primaryMedium.copy(
        fontSize = 36.sp
    ),
    val primaryRegular8: TextStyle = primaryRegular.copy(
        fontSize = 8.sp
    ),
    val primaryRegular10: TextStyle = primaryRegular.copy(
        fontSize = 10.sp
    ),
    val primaryRegular12: TextStyle = primaryRegular.copy(
        fontSize = 12.sp
    ),
    val primaryRegular14: TextStyle = primaryRegular.copy(
        fontSize = 14.sp
    ),
    val primaryRegular16: TextStyle = primaryRegular.copy(
        fontSize = 16.sp
    ),
    val primaryRegular18: TextStyle = primaryRegular.copy(
        fontSize = 18.sp
    ),
    val primaryRegular20: TextStyle = primaryRegular.copy(
        fontSize = 20.sp
    ),
    val primaryRegular22: TextStyle = primaryRegular.copy(
        fontSize = 22.sp
    ),
    val primaryRegular24: TextStyle = primaryRegular.copy(
        fontSize = 24.sp
    ),
    val primaryRegular26: TextStyle = primaryRegular.copy(
        fontSize = 26.sp
    ),
    val primaryRegular28: TextStyle = primaryRegular.copy(
        fontSize = 28.sp
    ),
    val primaryRegular30: TextStyle = primaryRegular.copy(
        fontSize = 30.sp
    ),
    val primaryRegular32: TextStyle = primaryRegular.copy(
        fontSize = 32.sp
    ),
    val primaryRegular34: TextStyle = primaryRegular.copy(
        fontSize = 34.sp
    ),
    val primaryRegular36: TextStyle = primaryRegular.copy(
        fontSize = 36.sp
    ),
    val primaryLight12: TextStyle = primaryLight.copy(
        fontSize = 12.sp
    ),
    val primaryLight14: TextStyle = primaryLight.copy(
        fontSize = 14.sp
    ),
    val primaryLight16: TextStyle = primaryLight.copy(
        fontSize = 16.sp
    ),
    val primaryLight18: TextStyle = primaryLight.copy(
        fontSize = 18.sp
    ),
    val primaryLight20: TextStyle = primaryLight.copy(
        fontSize = 20.sp
    ),
    val primaryLight22: TextStyle = primaryLight.copy(
        fontSize = 22.sp
    ),
    val primaryLight24: TextStyle = primaryLight.copy(
        fontSize = 24.sp
    ),
    val primaryLight26: TextStyle = primaryLight.copy(
        fontSize = 26.sp
    ),
    val primaryLight28: TextStyle = primaryLight.copy(
        fontSize = 28.sp
    ),
    val primaryLight30: TextStyle = primaryLight.copy(
        fontSize = 30.sp
    ),
    val primaryLight32: TextStyle = primaryLight.copy(
        fontSize = 32.sp
    ),
    val primaryLight34: TextStyle = primaryLight.copy(
        fontSize = 34.sp
    ),
    val primaryLight36: TextStyle = primaryLight.copy(
        fontSize = 36.sp
    ),
    val primaryThin12: TextStyle = primaryThin.copy(
        fontSize = 12.sp
    ),
    val primaryThin14: TextStyle = primaryThin.copy(
        fontSize = 14.sp
    ),
    val primaryThin16: TextStyle = primaryThin.copy(
        fontSize = 16.sp
    ),
    val primaryThin18: TextStyle = primaryThin.copy(
        fontSize = 18.sp
    ),
    val primaryThin20: TextStyle = primaryThin.copy(
        fontSize = 20.sp
    ),
    val primaryThin22: TextStyle = primaryThin.copy(
        fontSize = 22.sp
    ),
    val primaryThin24: TextStyle = primaryThin.copy(
        fontSize = 24.sp
    ),
    val primaryThin26: TextStyle = primaryThin.copy(
        fontSize = 26.sp
    ),
    val primaryThin28: TextStyle = primaryThin.copy(
        fontSize = 28.sp
    ),
    val primaryThin30: TextStyle = primaryThin.copy(
        fontSize = 30.sp
    ),
    val primaryThin32: TextStyle = primaryThin.copy(
        fontSize = 32.sp
    ),
    val primaryThin34: TextStyle = primaryThin.copy(
        fontSize = 34.sp
    ),
    val primaryThin36: TextStyle = primaryThin.copy(
        fontSize = 36.sp
    ),
)

val LocalAppTypography = staticCompositionLocalOf { AppTypography() }

fun AppTypography.getCoreTypography() = CoreTypography(
    primary = primary,
    bodyLarge = primaryBold22,
    bodyMedium = primaryRegular16
)