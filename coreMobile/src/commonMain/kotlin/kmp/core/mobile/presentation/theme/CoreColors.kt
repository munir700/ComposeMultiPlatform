package kmp.core.mobile.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val gold = Color(0xFFB68A35)
private val lightGold = Color(0xFFC3A05C)
private val californiaGold: Color = Color(0x99B68A35)
private val white = Color(0xFFFFFFFF)
private val gray = Color(0xFF909090)
private val darkGray = Color(0xFF494A4D)
private val lightGray = Color(0xFFB7B7B7)
private val lightGray30 = lightGray.copy(alpha = 0.3f)
private val red = Color(0xFFE11200)
private val lightRed: Color = Color(0xFFFEF3F2)
private val green = Color(0xFF4CAF50)
private val lightGreen: Color = Color(0xFFECFDF3)
private val shuttleGrey = Color(0xFF5F646D)
private val disabledGray = Color(0xFFB0B0B0)


@Immutable
data class CoreColors(
    val transparent: Color = Color.Transparent,
    val primary: Color = gold,
    val onPrimary: Color = white,
    val primaryContainer: Color = lightGold,
    val onPrimaryContainer: Color = white,

    val secondary: Color = darkGray,
    val onSecondary: Color = white,
    val secondaryContainer: Color = lightGray,
    val onSecondaryContainer: Color = white,

    val tertiary: Color = gray,
    val onTertiary: Color = white,
    val tertiaryContainer: Color = californiaGold,

    val error: Color = red,
    val mandatory: Color = red,
    val success: Color = green,
    val successContainer: Color = lightGreen,
    val background: Color = white,
    val stroke: Color = lightGray30,

    val uaePassBtn: Color = white,
    val navBarBg: Color = white,
    val activeNavBarItem: Color = gold,
    val inActiveNavBarItem: Color = shuttleGrey.copy(alpha = .75f),

    val sheetBackground: Color = white,
    val sheetPrimary: Color = shuttleGrey,

    val refreshIndicatorColor: Color = gold,
    val tertiaryStroke: Color = shuttleGrey.copy(alpha = 0.5f),
    val tertiaryColor: Color = Color(0xFF3E4028),

    val danger: Color = red,
    val dangerContainer: Color = lightRed,

    val pullRefreshIndicator: Color = gold,
    val pullRefreshIndicatorBackground: Color = white,

    val wheelPickerItemBg: Color = primary.copy(alpha = 0.5f),
    val wheelPickerItemStroke: Color = primary,
    val disabled: Color = disabledGray,
    val darkElectricBlue: Color = Color(0xFF58697D),

    // ItemPicker
    val itemPicker: ItemPickerColors = ItemPickerColors(
        textColor = darkGray,
        selectorColor = primary.copy(alpha = 0.3f),
        selectorBorderColor = primary
    ),

    // BottomSheetToolbar
    val bottomSheetToolbar: BottomSheetToolbarColors = BottomSheetToolbarColors(
        sheetPrimary = sheetPrimary,
        toolbarBackground = sheetBackground,
        background = sheetBackground,
    ),

    // PrimaryTextButton
    val primaryTextButton: PrimaryTextButtonColors = PrimaryTextButtonColors(
        primary = primary
    ),

    // SelectorProperties
    val selectorProperties: SelectorPropertiesColors = SelectorPropertiesColors(
        wheelPickerItemBg = wheelPickerItemBg,
        wheelPickerItemStroke = wheelPickerItemStroke
    ),

    // WheelTextPicker
    val wheelTextPicker: WheelTextPickerColors = WheelTextPickerColors(
        pickerItem = darkGray
    )
)

// ItemPicker
data class ItemPickerColors(
    val textColor: Color,
    val selectorColor: Color,
    val selectorBorderColor: Color
)

// BottomSheetToolbar
data class BottomSheetToolbarColors(
    private val sheetPrimary: Color,
    private val toolbarBackground: Color,
    private val background: Color,
    val startIconColor: Color = sheetPrimary,
    val doneActionColor: Color = sheetPrimary,
    val endIconColor: Color = sheetPrimary,
    val titleColor: Color = sheetPrimary,
    val toolbarBackgroundColor: Color = toolbarBackground,
    val backgroundColor: Color = background,
)

// PrimaryTextButton
data class PrimaryTextButtonColors(
    private val primary: Color,
    val textColor: Color = primary,
    val iconColor: Color = primary,
)

// SelectorProperties
data class SelectorPropertiesColors(
    private val wheelPickerItemBg: Color,
    private val wheelPickerItemStroke: Color,
    val color: Color = wheelPickerItemBg,
    val strokeColor: Color = wheelPickerItemStroke
)

// WheelTextPicker
data class WheelTextPickerColors(
    private val pickerItem: Color,
    val textColor: Color = pickerItem
)

val LocalCoreColors = staticCompositionLocalOf { CoreColors() }