package app.shared.mobile.presentation.infiniteScrolling


import app.shared.mobile.domain.repositories.InfiniteScrollRepository
import app.shared.mobile.presentation.base.AppBaseViewModel
import app.shared.mobile.presentation.infiniteScrolling.InfiniteScrollContract.Effect
import app.shared.mobile.presentation.infiniteScrolling.InfiniteScrollContract.Event
import app.shared.mobile.presentation.infiniteScrolling.InfiniteScrollContract.State
import kotlinx.coroutines.launch

class InfiniteScrollViewModel(private val repository: InfiniteScrollRepository) :
    AppBaseViewModel<State, Event, Effect>() {


    companion object {
        private const val PAGE_SIZE = 20
    }

    override fun setInitialState() = State()


    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()
        Event.LoadNextPage -> loadNextPage()
        Event.Retry -> retry()
        Event.RefreshList -> refreshList()
    }

    private fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        setState { copy(isInitialized = true) }
        loadInitialPage()
    }

    private fun loadInitialPage() {
        // Don't load if already loading
        if (currentState.isLoading) return

        setState {
            copy(
                isLoading = true,
                error = null
            )
        }

        viewModelScope.launch {
            repository.getItems(page = 0, pageSize = PAGE_SIZE)
                .fold(
                    onSuccess = { response ->
                        setState {
                            copy(
                                items = response.data,
                                currentPage = 0,
                                hasMorePages = response.hasMore,
                                isLoading = false,
                                error = null
                            )
                        }
                    },
                    onFailure = { error ->
                        setState {
                            copy(
                                isLoading = false,
                                error = error.message ?: "Unknown error occurred"
                            )
                        }
                        setEffect { Effect.ShowError(error.message ?: "Failed to load items") }
                    }
                )
        }
    }

    private fun loadNextPage() {
        // Don't load if already loading, no more pages, or have an error
        if (currentState.isLoadingMore ||
            !currentState.hasMorePages ||
            currentState.isLoading ||
            currentState.error != null
        ) {
            return
        }

        val nextPage = currentState.currentPage + 1

        setState { copy(isLoadingMore = true) }

        viewModelScope.launch {
            repository.getItems(page = nextPage, pageSize = PAGE_SIZE)
                .fold(
                    onSuccess = { response ->
                        setState {
                            copy(
                                items = items + response.data,
                                currentPage = nextPage,
                                hasMorePages = response.hasMore,
                                isLoadingMore = false,
                                error = null
                            )
                        }
                    },
                    onFailure = { error ->
                        setState {
                            copy(
                                isLoadingMore = false,
                                error = error.message ?: "Unknown error occurred"
                            )
                        }
                        setEffect { Effect.ShowError(error.message ?: "Failed to load more items") }
                    }
                )
        }
    }

    private fun retry() {
        // Clear error and try loading again
        setState { copy(error = null) }

        if (currentState.items.isEmpty()) {
            // If no items, retry initial load
            loadInitialPage()
        } else {
            // If we have items, retry loading next page
            loadNextPage()
        }
    }

    private fun refreshList() {
        // Reset to initial state and reload
        setState {
            copy(
                items = emptyList(),
                currentPage = 0,
                hasMorePages = true,
                error = null
            )
        }
        loadInitialPage()
    }
}
