package app.shared.mobile.presentation.components.fields


import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import app.shared.mobile.presentation.theme.colors
import app.shared.mobile.presentation.theme.spacings
import app.shared.mobile.presentation.theme.typography
import kmp.core.mobile.presentation.components.fields.PrimaryTextInputField

@Composable
internal fun PrimaryInputField(
    modifier: Modifier = Modifier,
    text: String,
    label: String? = null,
    onValueChange: ((String) -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    onStartIconClick: (() -> Unit)? = null,
    onEndIconClick: (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    showMandatory: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxLength: Int = Int.MAX_VALUE,
    maxLines: Int = Int.MAX_VALUE,
    error: String? = null,
    isError: Boolean = false,
    endIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    startIconPainter: Painter? = null,
    placeholder: String? = null,
    placeholderMaxLines: Int = 1,
    textAlign: TextAlign? = null,
    allowDigitsOnly: Boolean = false,
    isStaticLabel: Boolean = true,
    minHeight: Dp = spacings.spacing0,
    iconTintColor: Color? = null,
) {
    PrimaryTextInputField(
        modifier = modifier,
        text = text,
        label = label,
        onValueChange = onValueChange,
        onClick = onClick,
        onStartIconClick = onStartIconClick,
        onEndIconClick = onEndIconClick,
        keyboardType = keyboardType,
        imeAction = imeAction,
        keyboardActions = keyboardActions,
        capitalization = capitalization,
        singleLine = singleLine,
        enabled = enabled,
        readOnly = readOnly,
        showMandatory = showMandatory,
        visualTransformation = visualTransformation,
        maxLength = maxLength,
        maxLines = maxLines,
        error = error,
        isError = isError,
        endIconVector = endIconVector,
        endIconPainter = endIconPainter,
        startIconVector = startIconVector,
        startIconPainter = startIconPainter,
        placeholder = placeholder,
        placeholderMaxLines = placeholderMaxLines,
        textAlign = textAlign,
        allowDigitsOnly = allowDigitsOnly,
        isStaticLabel = isStaticLabel,
        textColor = colors.slateGray,
        unfocusedBorderColor = colors.freshAir,
        minHeight = minHeight,
        iconTintColor = iconTintColor,
        labelTextStyle = typography.primaryRegular16.copy(
            color = colors.davyGrey
        )
    )
}