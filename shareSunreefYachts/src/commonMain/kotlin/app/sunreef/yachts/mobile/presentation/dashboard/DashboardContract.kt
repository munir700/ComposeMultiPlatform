package app.sunreef.yachts.mobile.presentation.dashboard

import kmp.core.mobile.base.ViewEvent
import kmp.core.mobile.base.ViewSideEffect
import kmp.core.mobile.base.ViewState
import app.sunreef.yachts.mobile.domain.models.YachtSystem

/**
 * Dashboard Contract
 * Defines State, Events, and Side Effects for Dashboard screen
 * Follows the same MVVM Contract pattern as login in shared module
 */

class DashboardContract {
    data class State(
        val isInitialized: Boolean = false,
        val isLoading: Boolean = false,
        val systems: List<YachtSystem> = emptyList(),
        val error: String? = null,
        val systemStatus: String = "Initializing..."
    ) : ViewState

    sealed class Event : ViewEvent {
        data object Init : Event()
        data object RefreshSystems : Event()
        data class SystemClicked(val systemId: String) : Event()
        data object SettingsClicked : Event()
        data object BackClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        data class NavigateToSystem(val systemId: String) : Effect()
        data object NavigateToSettings : Effect()
    }
}

