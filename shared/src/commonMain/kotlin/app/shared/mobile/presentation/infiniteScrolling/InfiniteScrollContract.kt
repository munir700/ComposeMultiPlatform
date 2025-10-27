package app.shared.mobile.presentation.infiniteScrolling

import app.shared.mobile.data.remote.models.ItemModel
import kmp.core.mobile.base.ViewEvent
import kmp.core.mobile.base.ViewSideEffect
import kmp.core.mobile.base.ViewState

class InfiniteScrollContract {
    data class State(
        val isInitialized: Boolean = false,
        val isLoading: Boolean = false,
        val isLoadingMore: Boolean = false,
        val error: String? = null,
        val hasMorePages: Boolean = true,
        val currentPage: Int = 0,
        val items: List<ItemModel> = emptyList(),
    ) : ViewState

    sealed class Event : ViewEvent {
        data object Init : Event()
        data object LoadNextPage : Event()
        data object Retry : Event()
        data object RefreshList : Event()
    }

    sealed class Effect : ViewSideEffect {
        data class ShowError(val message: String) : Effect()
    }
}