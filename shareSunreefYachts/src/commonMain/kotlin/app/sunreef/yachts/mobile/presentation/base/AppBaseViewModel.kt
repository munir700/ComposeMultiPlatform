package app.sunreef.yachts.mobile.presentation.base

import app.sunreef.yachts.mobile.globalState.IAppGlobalState
import kmp.core.mobile.base.CoreViewModel
import kmp.core.mobile.base.ViewEvent
import kmp.core.mobile.base.ViewSideEffect
import kmp.core.mobile.base.ViewState
import org.koin.core.component.inject

/**
 * Base ViewModel for Sunreef Yachts
 * Provides access to global state and core functionality
 * Follows the same pattern as shared module's AppBaseViewModel
 */

abstract class AppBaseViewModel<S : ViewState, E : ViewEvent, SF : ViewSideEffect>() :
    CoreViewModel<S, E, SF>() {

    protected val globalState by inject<IAppGlobalState>()
}