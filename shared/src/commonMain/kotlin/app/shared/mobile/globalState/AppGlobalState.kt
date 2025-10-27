package app.shared.mobile.globalState


import androidx.compose.runtime.mutableStateOf
import kmp.core.mobile.globalState.CoreGlobalState

class AppGlobalState : CoreGlobalState(), IAppGlobalState {
    // Add app specific global state
    override val bottomNavbarHeight = mutableStateOf(0)

    override fun setBottomNavbarHeight(height: Int) {
        bottomNavbarHeight.value = height
    }
}