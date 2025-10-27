package app.shared.mobile.presentation.theme


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.LayoutDirection
import kmp.core.mobile.language.ILanguageManager
import kmp.core.mobile.presentation.theme.LocalCoreColors
import kmp.core.mobile.presentation.theme.LocalCoreShapes
import kmp.core.mobile.presentation.theme.LocalCoreSpacings
import kmp.core.mobile.presentation.theme.LocalCoreTypography
import org.koin.compose.koinInject

val spacings
    @Composable
    @ReadOnlyComposable
    get() = LocalAppSpacings.current

val colors
    @Composable
    @ReadOnlyComposable
    get() = LocalAppColors.current

val typography
    @Composable
    @ReadOnlyComposable
    get() = LocalAppTypography.current

val shapes
    @Composable
    @ReadOnlyComposable
    get() = LocalAppShapes.current

@Composable
private fun ProvideTheme(
    content: @Composable () -> Unit
) {
    // Prepare language and layout direction stuff
    val languageManager = koinInject<ILanguageManager>()
    val currentLanguage by remember { languageManager.currentLanguageAsState() }
    val layoutDirection = remember(currentLanguage) {
        if (currentLanguage?.isRtl == true) LayoutDirection.Rtl else LayoutDirection.Ltr
    }

    // Get font families
    val alexFontFamily = getAlexandriaFontFamily()

    // Create app & core typographies
    val appTypography = remember(alexFontFamily) {
        AppTypography(
            primary = TextStyle(fontFamily = alexFontFamily)
        )
    }
    val coreTypography = remember(alexFontFamily) {
        appTypography.getCoreTypography()
    }

    // Create other app & core theme sets
    val appColors = remember { AppColors() }
    val appShapes = remember { AppShapes() }
    val appSpacings = remember { AppSpacings() }

    CompositionLocalProvider(
        LocalAppColors provides appColors,
        LocalAppShapes provides appShapes,
        LocalAppSpacings provides appSpacings,
        LocalCoreColors provides appColors.getCoreColors(),
        LocalCoreShapes provides appShapes.getCoreShapes(),
        LocalCoreSpacings provides appSpacings.getCoreSpacings(),
        LocalAppTypography provides appTypography,
        LocalCoreTypography provides coreTypography,
        LocalLayoutDirection provides layoutDirection
    ) {
        key(
            layoutDirection,
            currentLanguage,
            appTypography,
            coreTypography
        ) {
            content()
        }
    }
}

@Composable
internal fun AppThemeContent(
    content: @Composable () -> Unit
) {
    val materialColorScheme = lightColorScheme(
        primary = colors.tiffanyBlue,
        secondary = colors.darkGray,
        background = colors.background,
        surface = colors.background
    )

    // Provide different theme implementations here if you need
    ProvideTheme {
        MaterialTheme(
            colorScheme = materialColorScheme
        ) {
            Scaffold {
                content()
            }
        }
    }
}