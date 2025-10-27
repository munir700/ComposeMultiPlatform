package kmp.core.mobile.presentation.app


import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import kmp.core.mobile.utils.extensions.dismissKeyboard
import kmp.core.mobile.utils.extensions.setNavigationBarColor
import kmp.core.mobile.utils.extensions.setStatusBarColor

@Composable
internal fun ConfigAppUI(
    dismissKeyboardState: State<Boolean>,
    isStatusBarDarkState: State<Boolean>,
    isNavigationBarDarkState: State<Boolean>,
    onResetKeyboardState: () -> Unit
) {
    // Handle dismiss keyboard state
    if (dismissKeyboardState.value) {
        onResetKeyboardState.invoke()
        dismissKeyboard()
    }

    // Handle changing status bar color
    setStatusBarColor(
        isDark = isStatusBarDarkState.value
    )

    // Handle changing navigation bar color
    setNavigationBarColor(
        isDark = isNavigationBarDarkState.value
    )
}
