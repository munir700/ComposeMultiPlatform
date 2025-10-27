package app.shared.mobile.presentation.base


import app.shared.mobile.globalState.IAppGlobalState
import kmp.core.mobile.base.CoreViewModel
import kmp.core.mobile.base.ViewEvent
import kmp.core.mobile.base.ViewSideEffect
import kmp.core.mobile.base.ViewState
import org.koin.core.component.inject


abstract class AppBaseViewModel<S : ViewState, E : ViewEvent, SF : ViewSideEffect>() :
    CoreViewModel<S, E, SF>() {

    protected val globalState by inject<IAppGlobalState>()
}