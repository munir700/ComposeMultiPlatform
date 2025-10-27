package app.shared.mobile.globalState


import androidx.compose.runtime.State
import kmp.core.mobile.globalState.ICoreGlobalState


interface IAppGlobalState : ICoreGlobalState {
    // Add app specific global state
    val bottomNavbarHeight: State<Int>

    fun setBottomNavbarHeight(height: Int)
}