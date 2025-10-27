package kmp.core.mobile.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable


val spacings
    @Composable
    @ReadOnlyComposable
    get() = LocalCoreSpacings.current

val colors
    @Composable
    @ReadOnlyComposable
    get() = LocalCoreColors.current

val typography
    @Composable
    @ReadOnlyComposable
    get() = LocalCoreTypography.current

val shapes
    @Composable
    @ReadOnlyComposable
    get() = LocalCoreShapes.current
