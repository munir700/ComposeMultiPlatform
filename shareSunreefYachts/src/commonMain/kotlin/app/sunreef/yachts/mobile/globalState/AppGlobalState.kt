package app.sunreef.yachts.mobile.globalState

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kmp.core.mobile.globalState.CoreGlobalState

/**
 * App Global State Implementation
 * Extends CoreGlobalState and implements IAppGlobalState
 * Manages application-level state including navigation and UI state
 */

class AppGlobalState : CoreGlobalState(), IAppGlobalState {
    // App specific global state
    private val _bottomNavbarHeight = mutableStateOf(0)
    override val bottomNavbarHeight: State<Int> = _bottomNavbarHeight

    override fun setBottomNavbarHeight(height: Int) {
        _bottomNavbarHeight.value = height
    }
}