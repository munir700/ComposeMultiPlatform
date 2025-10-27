package kmp.core.mobile.base

import androidx.compose.runtime.Composable

abstract class BaseSheet<T : CoreViewModel<*, *, *>>() : BaseScreen<T>() {

    @Composable
    override fun Content() {
        // Default empty implementation for sheets
    }
}