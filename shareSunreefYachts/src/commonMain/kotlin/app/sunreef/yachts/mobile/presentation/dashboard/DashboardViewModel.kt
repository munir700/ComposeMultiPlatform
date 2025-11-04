package app.sunreef.yachts.mobile.presentation.dashboard

import app.sunreef.yachts.mobile.presentation.base.AppBaseViewModel
import app.sunreef.yachts.mobile.presentation.dashboard.DashboardContract.Effect
import app.sunreef.yachts.mobile.presentation.dashboard.DashboardContract.Event
import app.sunreef.yachts.mobile.presentation.dashboard.DashboardContract.State
import app.sunreef.yachts.mobile.domain.usecases.GetYachtSystemsUseCase

/**
 * Dashboard ViewModel
 * Handles dashboard state and business logic
 * Follows the same pattern as LoginViewModel in shared module
 */

class DashboardViewModel(
    private val getYachtSystemsUseCase: GetYachtSystemsUseCase
) : AppBaseViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()
        Event.RefreshSystems -> refreshSystems()
        is Event.SystemClicked -> handleSystemClicked(event.systemId)
        Event.SettingsClicked -> handleSettingsClicked()
        Event.BackClicked -> handleBackClick()
    }

    private fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        setState { copy(isInitialized = true) }
        loadYachtSystems()
    }

    private fun loadYachtSystems() = executeSafe({
        setState { copy(isLoading = true) }

        try {
            val result = getYachtSystemsUseCase()
            result.onSuccess { systems ->
                setState {
                    copy(
                        systems = systems,
                        isLoading = false,
                        error = null,
                        systemStatus = "All Systems Online"
                    )
                }
            }.onFailure { error ->
                setState {
                    copy(
                        isLoading = false,
                        error = error.message ?: "Failed to load systems",
                        systemStatus = "Error Loading Systems"
                    )
                }
            }
        } catch (e: Exception) {
            setState {
                copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error",
                    systemStatus = "Error"
                )
            }
        }
    })

    private fun refreshSystems() {
        loadYachtSystems()
    }

    private fun handleSystemClicked(systemId: String) {
        // Navigation will be handled through effects
        setEffect { Effect.NavigateToSystem(systemId) }
    }

    private fun handleSettingsClicked() {
        setEffect { Effect.NavigateToSettings }
    }

    private fun handleBackClick() {
        navManager.goBack()
    }
}

