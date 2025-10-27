package kmp.core.mobile.base

import androidx.compose.ui.graphics.vector.ImageVector

abstract class BaseTabScreen<T : CoreViewModel<*, *, *>>() : BaseScreen<T>() {

    open val tabTitle: String = ""
    open val tabIconRes: String? = null
    open val tabIconVector: ImageVector? = null

    open fun onDisplayed() {
    }
}