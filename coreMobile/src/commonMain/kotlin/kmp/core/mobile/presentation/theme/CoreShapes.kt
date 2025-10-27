package kmp.core.mobile.presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
data class CoreShapes(
    val xSmall: RoundedCornerShape = RoundedCornerShape(8.dp),
    val small: RoundedCornerShape = RoundedCornerShape(10.dp),
    val medium: RoundedCornerShape = RoundedCornerShape(12.dp),
    val large: RoundedCornerShape = RoundedCornerShape(14.dp),
    val xLarge: RoundedCornerShape = RoundedCornerShape(16.dp),
    val bottomSheet: RoundedCornerShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
    val wheelPickerItem: RoundedCornerShape = RoundedCornerShape(0.dp),

    // SelectorProperties
    val selectorProperties: SelectorPropertiesShapes = SelectorPropertiesShapes(
        wheelPickerItem = wheelPickerItem
    ),
)

// SelectorProperties
data class SelectorPropertiesShapes(
    private val wheelPickerItem: RoundedCornerShape,
    val shape: RoundedCornerShape = wheelPickerItem
)

val LocalCoreShapes = staticCompositionLocalOf { CoreShapes() }