package kmp.core.mobile.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Define components typography
@Immutable
data class CoreTypography(
    val primary: TextStyle = TextStyle(),
    val primaryBold: TextStyle = primary.copy(
        fontWeight = FontWeight.Bold
    ),
    val primarySemiBold: TextStyle = primary.copy(
        fontWeight = FontWeight.SemiBold
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
    val headline: TextStyle = primaryBold.copy(
        fontSize = 24.sp
    ),
    val primaryBold14: TextStyle = primaryBold.copy(
        fontSize = 14.sp
    ),
    val sheetTitle: TextStyle = primaryMedium.copy(
        fontSize = 18.sp
    ),
    val sheetSubTitle: TextStyle = primaryRegular.copy(
        fontSize = 16.sp
    ),
    val labelMedium: TextStyle = primaryRegular.copy(
        fontSize = 14.sp
    ),
    val labelSmall: TextStyle = primaryRegular.copy(
        fontSize = 12.sp
    ),
    val bodyXLarge: TextStyle = primaryMedium.copy(
        fontSize = 20.sp
    ),
    val bodyLarge: TextStyle = primaryMedium.copy(
        fontSize = 16.sp
    ),
    val bodyMedium: TextStyle = primaryMedium.copy(
        fontSize = 14.sp
    ),
    val bodySmall: TextStyle = primaryMedium.copy(
        fontSize = 12.sp
    ),
    val bodyNormal: TextStyle = primaryMedium.copy(
        fontSize = 20.sp
    ),
    val activeNavBarItem: TextStyle = primaryRegular.copy(
        fontSize = 10.sp
    ),
    val inActiveNavBarItem: TextStyle = primaryRegular.copy(
        fontSize = 10.sp
    ),
    val snackBarMsg: TextStyle = primaryRegular.copy(
        fontSize = 16.sp
    ),
    val sheetAction: TextStyle = primarySemiBold.copy(
        fontSize = 18.sp
    ),
    val primaryMedium16: TextStyle = primaryMedium.copy(
        fontSize = 16.sp
    ),

    // PrimaryTextButton
    val primaryTextButton: PrimaryTextButtonTypography = PrimaryTextButtonTypography(
        btnLabelSmall = primarySemiBold.copy(fontSize = 16.sp)
    ),

    // ItemPicker
    val itemPicker: ItemPickerTypography = ItemPickerTypography(
        pickerItem = primaryMedium.copy(fontSize = 20.sp)
    ),

    // WheelTextPicker
    val wheelTextPicker: WheelTextPickerTypography = WheelTextPickerTypography(
        pickerItem = primaryMedium.copy(fontSize = 20.sp)
    ),
)

// PrimaryTextButton
data class PrimaryTextButtonTypography(
    private val btnLabelSmall: TextStyle,
    val textStyle: TextStyle = btnLabelSmall
)

// ItemPicker
data class ItemPickerTypography(
    private val pickerItem: TextStyle,
    val textStyle: TextStyle = pickerItem
)

// WheelTextPicker
data class WheelTextPickerTypography(
    private val pickerItem: TextStyle,
    val textStyle: TextStyle = pickerItem
)

val LocalCoreTypography = staticCompositionLocalOf { CoreTypography() }