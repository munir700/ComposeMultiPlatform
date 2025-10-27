package kmp.core.mobile.presentation.components.fields


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import kmp.core.mobile.presentation.theme.colors
import kmp.core.mobile.presentation.theme.spacings
import kmp.core.mobile.utils.extensions.noRippleClickable
import kmp.core.mobile.utils.extensions.removeAllNonNumeric
import kmp.core.mobile.utils.extensions.tintIfNotNull
import kmp.core.mobile.utils.extensions.toDp

@Composable
internal fun BaseTextInputField(
    text: String,
    label: String?,
    isStaticLabel: Boolean,
    showMandatory: Boolean,
    onValueChange: ((String) -> Unit)?,
    onClick: (() -> Unit)?,
    onStartIconClick: (() -> Unit)?,
    onEndIconClick: (() -> Unit)?,
    modifier: Modifier,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    keyboardActions: KeyboardActions,
    capitalization: KeyboardCapitalization,
    backgroundColor: Color,
    focusedBorderColor: Color,
    unfocusedBorderColor: Color = colors.secondaryContainer,
    singleLine: Boolean,
    enabled: Boolean,
    readOnly: Boolean,
    visualTransformation: VisualTransformation,
    textColor: Color,
    maxLength: Int,
    maxLines: Int,
    minHeight: Dp,
    error: String?,
    isError: Boolean = false,
    endIconVector: ImageVector?,
    endIconPainter: Painter?,
    endIconSize: Dp,
    startIconVector: ImageVector?,
    startIconPainter: Painter?,
    startIconSize: Dp,
    iconTintColor: Color?,
    placeholder: String?,
    placeholderMaxLines: Int,
    shape: RoundedCornerShape,
    placeholderTextStyle: TextStyle,
    textStyle: TextStyle,
    textAlign: TextAlign?,
    labelTextStyle: TextStyle,
    errorTextStyle: TextStyle,
    allowDigitsOnly: Boolean
) {
    // Prepare field box size state
    var fieldBoxSize by remember {
        mutableStateOf(IntSize.Zero)
    }

    // Prepare text value state
    var textFieldValueState by remember {
        mutableStateOf(
            TextFieldValue(
                text = text, selection = TextRange(text.length)
            )
        )
    }
    if (textFieldValueState.text != text) {
        textFieldValueState = TextFieldValue(
            text = text, selection = TextRange(text.length)
        )
    }

    // Prepare leading image
    val leadingImage: @Composable (() -> Unit)? = if (startIconVector != null) {
        {
            Image(
                imageVector = startIconVector,
                contentDescription = null,
                colorFilter = tintIfNotNull(iconTintColor),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(startIconSize)
                    .noRippleClickable(onClick = onStartIconClick)
            )
        }
    } else if (startIconPainter != null) {
        {
            Image(
                painter = startIconPainter,
                contentDescription = null,
                colorFilter = tintIfNotNull(iconTintColor),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(startIconSize)
                    .noRippleClickable(onClick = onStartIconClick)
            )
        }
    } else null

    // Prepare trailing image
    val trailingImage: @Composable (() -> Unit)? = if (endIconVector != null) {
        {
            Image(
                imageVector = endIconVector,
                contentDescription = null,
                colorFilter = tintIfNotNull(iconTintColor),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(endIconSize)
                    .noRippleClickable(onClick = onEndIconClick)
            )
        }
    } else if (endIconPainter != null) {
        {
            Image(
                painter = endIconPainter,
                contentDescription = null,
                colorFilter = tintIfNotNull(iconTintColor),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(endIconSize)
                    .noRippleClickable(onClick = onEndIconClick)
            )
        }
    } else null

    // Prepare placeholder text
    val placeholderText: @Composable (() -> Unit)? = if (placeholder != null) {
        {
            Text(
                text = placeholder,
                style = placeholderTextStyle,
                maxLines = placeholderMaxLines,
                overflow = TextOverflow.Ellipsis
            )
        }
    } else null

    // Prepare label text
    val labelText: @Composable (() -> Unit)? = if (isStaticLabel.not() && label != null) {
        {
            Text(
                text = label,
                style = labelTextStyle
            )
        }
    } else null

    // Prepare value change handler
    val valueChangeHandler: (TextFieldValue) -> Unit = remember {
        {
            // Get input text
            var inputText = it.text

            // Handle allow digits only validation
            if (allowDigitsOnly) {
                inputText = inputText.removeAllNonNumeric()
            }

            // Handle length validation
            if (inputText.length > maxLength) {
                inputText = inputText.substring(0, maxLength)
            }

            // Then invoke value changed
            onValueChange?.invoke(inputText)

            // Update field state
            textFieldValueState = it
        }
    }

    // Container column
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spacings.paddingSmall)
    ) {
        // Render static label if required
        if (label != null && isStaticLabel) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showMandatory) {
                    Text(
                        text = "*",
                        style = labelTextStyle,
                        color = colors.mandatory
                    )
                }

                Text(
                    text = label,
                    style = labelTextStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // Render text field box container
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged { fieldBoxSize = it }
        ) {
            // Render text field
            OutlinedTextField(
                textStyle = textStyle.copy(
                    textAlign = textAlign ?: TextAlign.Unspecified
                ),
                readOnly = readOnly,
                value = textFieldValueState,
                isError = error != null || isError,
                keyboardActions = keyboardActions,
                singleLine = singleLine,
                maxLines = maxLines,
                enabled = enabled,
                onValueChange = valueChangeHandler,
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = imeAction,
                    capitalization = capitalization
                ),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    focusedSupportingTextColor = textColor,
                    focusedContainerColor = backgroundColor,
                    unfocusedContainerColor = backgroundColor,
                    disabledContainerColor = backgroundColor,
                    errorContainerColor = backgroundColor,
                    focusedIndicatorColor = focusedBorderColor,
                    unfocusedIndicatorColor = unfocusedBorderColor,
                    disabledIndicatorColor = unfocusedBorderColor,
                    errorIndicatorColor = colors.error
                ),
                shape = shape,
                visualTransformation = visualTransformation,
                leadingIcon = leadingImage,
                trailingIcon = trailingImage,
                label = labelText,
                placeholder = placeholderText,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = minHeight)
            )

            // Check if we need clickable layer
            if (onClick != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(fieldBoxSize.height.toDp())
                        .noRippleClickable(onClick = onClick)
                )
            }
        }

        // Render error if required
        AnimatedVisibility(error != null) {
            Text(
                text = error.orEmpty(),
                style = errorTextStyle,
                maxLines = placeholderMaxLines,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}