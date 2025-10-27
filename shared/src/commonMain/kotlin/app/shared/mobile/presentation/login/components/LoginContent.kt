package app.shared.mobile.presentation.login.components


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import app.shared.mobile.presentation.components.Toolbar
import app.shared.mobile.presentation.login.LoginContract.Event
import app.shared.mobile.presentation.login.LoginContract.State
import app.shared.mobile.presentation.theme.colors
import app.shared.mobile.presentation.theme.shapes
import app.shared.mobile.presentation.theme.spacings
import app.shared.mobile.presentation.theme.typography
import app.shared.mobile.resources.*
import kmp.core.mobile.presentation.buttons.FilledPrimaryButton
import kmp.core.mobile.presentation.components.fields.PrimaryTextInputField
import kmp.core.mobile.presentation.container.SafeInsetsColumn
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LoginContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Render container Scrollable column
    SafeInsetsColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = spacings.spacing20)
    ) {
        // Back icon
        Toolbar(
            title = stringResource(Res.string.login),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing10),
            onBackClick = {
                onEvent.invoke(Event.BackClicked)
            }
        )


        // Input field to user name
        PrimaryTextInputField(
            isStaticLabel = true,
            text = state.userName.orEmpty(),
            label = stringResource(Res.string.user_name),
            placeholder = stringResource(Res.string.please_enter_the_user_name),
            error = state.userNameError,
            textStyle = typography.primaryRegular16,
            shape = shapes.roundedCorner16,
            focusedBorderColor = colors.hokeyPokey.copy(alpha = .5f),
            textColor = colors.stormGrey,
            onValueChange = { input -> onEvent.invoke(Event.UserNameChanged(userName = input)) },
            imeAction = ImeAction.Next,
            modifier = Modifier.fillMaxWidth().padding(top = spacings.spacing150)
        )

        // Input field to user name
        PrimaryTextInputField(
            isStaticLabel = true,
            text = state.password.orEmpty(),
            label = stringResource(Res.string.password),
            placeholder = stringResource(Res.string.please_enter_the_password),
            error = state.passwordError,
            textStyle = typography.primaryRegular16,
            shape = shapes.roundedCorner16,
            focusedBorderColor = colors.hokeyPokey.copy(alpha = .5f),
            textColor = colors.stormGrey,
            onValueChange = { input -> onEvent.invoke(Event.PasswordChanged(password = input)) },
            imeAction = ImeAction.Done,
            modifier = Modifier.fillMaxWidth().padding(top = spacings.spacing24)
        )

        // Bottom space
        Spacer(modifier = Modifier.weight(1f))

        // Positive button
        FilledPrimaryButton(
            text = stringResource(Res.string.login),
            isSmallHeight = false,
            textStyle = typography.primaryBold16,
            onClick = {
                onEvent.invoke(Event.Login)
            },
            modifier = Modifier.fillMaxWidth()
        )
        // Bottom space
        Spacer(modifier = Modifier.weight(1f))
    }
}

