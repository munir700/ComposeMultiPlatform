package kmp.core.mobile.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class CoreSpacings(
    val noSpacing: Dp = 0.dp,
    val strokePoint15: Dp = 0.15.dp,
    val stroke: Dp = 1.dp,
    val tabIndicatorSize: Dp = 8.dp,
    val spacing8: Dp = 8.dp,
    val spacing12: Dp = 12.dp,
    val spacing16: Dp = 16.dp,
    val spacing24: Dp = 24.dp,
    val spacing32: Dp = 32.dp,

    val paddingMin: Dp = 3.dp,
    val paddingXXSmall: Dp = 2.dp,
    val paddingXSmall: Dp = 4.dp,
    val paddingSmall: Dp = 6.dp,
    val padding: Dp = 8.dp,
    val paddingMedium: Dp = 10.dp,
    val paddingLarge: Dp = 12.dp,
    val paddingXLarge: Dp = 16.dp,
    val paddingXXLarge: Dp = 20.dp,
    val paddingXXXLarge: Dp = 24.dp,

    val icon: Dp = 8.dp,
    val iconMin: Dp = 14.dp,
    val iconSmall: Dp = 18.dp,
    val iconMedium: Dp = 20.dp,
    val iconLarge: Dp = 24.dp,
    val iconXLarge: Dp = 32.dp,

    val switchBtnHeight: Dp = 20.dp,
    val switchBtnWidth: Dp = 40.dp,
    val btnMinHeightSmall: Dp = 46.dp,
    val btnMinHeightNormal: Dp = 56.dp,

    val lottieProgressSize: Dp = 70.dp,
    val progressSizeNormal: Dp = 44.dp,
    val progressSizeSmall: Dp = 24.dp,
    val progressStrokeNormal: Dp = 8.dp,
    val progressStrokeSmall: Dp = 6.dp,

    val popupIconLarge: Dp = 100.dp,
    val popupPadding: Dp = 30.dp,
    val popupSpacingLarge: Dp = 32.dp,
    val popupSpacingMedium: Dp = 24.dp,

    val sheetElevation: Dp = 12.dp,
    val sheetPaddingHorizontal: Dp = 24.dp,
    val sheetPaddingVertical: Dp = 32.dp,
    val sheetHandleWidth: Dp = 26.dp,
    val sheetHandlePadding: Dp = 10.dp,

    val strokeIconSize: Dp = 10.dp,
    val navBarHeight: Dp = 64.dp,
    val pickerItemHeight: Dp = 40.dp,
    val screenPadding: Dp = 16.dp,
    val datePickerHeight: Dp = 500.dp,
    val datePickerDialogHeight: Dp = 550.dp,
    val otpFieldCharWidth: Dp = 44.dp,

    // BottomSheetToolbar
    val bottomSheetToolbar: BottomSheetToolbarSpacings = BottomSheetToolbarSpacings(
        iconLarge = iconLarge,
        paddingXLarge = paddingXLarge,
        paddingXXSmall = paddingXXSmall,
        paddingLarge = paddingLarge
    ),

    // PrimaryTextButton
    val primaryTextButton: PrimaryTextButtonSpacings = PrimaryTextButtonSpacings(
        iconMedium = iconMedium,
        textBtnSpacing = 8.dp,
        btnPaddingVertical = 4.dp
    ),

    // ItemPicker
    val itemPicker: ItemPickerSpacings = ItemPickerSpacings(
        paddingXLarge = paddingXLarge,
        itemPickerItemStroke = 0.dp
    ),

    // WheelPicker
    val wheelPicker: WheelPickerSpacings = WheelPickerSpacings(
        defaultWheelPickerWidth = 256.dp,
        defaultWheelPickerHeight = 270.dp
    ),

    // SelectorProperties
    val selectorProperties: SelectorPropertiesSpacings = SelectorPropertiesSpacings(
        wheelPickerItemStroke = 1.dp
    ),

    // WheelTextPicker
    val wheelTextPicker: WheelTextPickerSpacings = WheelTextPickerSpacings(
        defaultWheelPickerWidth = 256.dp,
        defaultWheelPickerHeight = 270.dp
    ),
)

// BottomSheetToolbar
data class BottomSheetToolbarSpacings(
    private val iconLarge: Dp,
    private val paddingXLarge: Dp,
    private val paddingXXSmall: Dp,
    private val paddingLarge: Dp,
    val iconSize: Dp = iconLarge,
    val boxPadding: Dp = paddingXLarge,
    val startIconPadding: Dp = paddingXXSmall,
    val endIconPadding: Dp = paddingXXSmall,
    val titlePadding: Dp = paddingLarge
)

// PrimaryTextButton
data class PrimaryTextButtonSpacings(
    private val iconMedium: Dp,
    private val textBtnSpacing: Dp,
    private val btnPaddingVertical: Dp,
    val iconSize: Dp = iconMedium,
    val spacing: Dp = textBtnSpacing,
    val paddingVertical: Dp = btnPaddingVertical
)

// ItemPicker
data class ItemPickerSpacings(
    private val paddingXLarge: Dp,
    private val itemPickerItemStroke: Dp,
    val searchFieldPadding: Dp = paddingXLarge,
    val selectorBorderWidth: Dp = itemPickerItemStroke,
    val wheelHeight: Dp = 270.dp
)

// WheelPicker
data class WheelPickerSpacings(
    private val defaultWheelPickerWidth: Dp,
    private val defaultWheelPickerHeight: Dp,
    val pickerWidth: Dp = defaultWheelPickerWidth,
    val pickerHeight: Dp = defaultWheelPickerHeight
)

// SelectorProperties
data class SelectorPropertiesSpacings(
    private val wheelPickerItemStroke: Dp,
    val borderWidth: Dp = wheelPickerItemStroke
)

// WheelTextPicker
data class WheelTextPickerSpacings(
    private val defaultWheelPickerWidth: Dp,
    private val defaultWheelPickerHeight: Dp,
    val pickerWidth: Dp = defaultWheelPickerWidth,
    val pickerHeight: Dp = defaultWheelPickerHeight
)

val LocalCoreSpacings = staticCompositionLocalOf { CoreSpacings() }