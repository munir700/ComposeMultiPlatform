package app.shared.mobile.presentation.infiniteScrolling.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.shared.mobile.presentation.infiniteScrolling.InfiniteScrollContract.Event
import app.shared.mobile.presentation.infiniteScrolling.InfiniteScrollContract.State
import app.shared.mobile.presentation.theme.spacings
import app.shared.mobile.presentation.components.scroll.EndOfListView
import app.shared.mobile.presentation.components.scroll.ErrorView
import app.shared.mobile.presentation.components.scroll.InfiniteScrollHandler
import app.shared.mobile.presentation.components.scroll.InitialLoadingView
import app.shared.mobile.presentation.components.scroll.LoadMoreErrorView
import app.shared.mobile.presentation.components.scroll.LoadingMoreView

@Composable
internal fun InfiniteScrollContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    val listState = rememberLazyListState()

    // Trigger pagination when user scrolls near bottom
    InfiniteScrollHandler(
        listState = listState,
        buffer = 3, // Load more when 3 items from bottom
        onLoadMore = { onEvent(Event.LoadNextPage) }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            // Initial loading state
            state.isLoading && state.items.isEmpty() -> {
                InitialLoadingView()
            }

            // Error state with no items
            state.error != null && state.items.isEmpty() -> {
                ErrorView(
                    message = state.error,
                    onRetry = { onEvent(Event.Retry) }
                )
            }

            // List with items
            else -> {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(spacings.spacing16),
                    verticalArrangement = Arrangement.spacedBy(spacings.spacing12)
                ) {
                    items(
                        items = state.items,
                        key = { item -> item.id }
                    ) { item ->
                        ItemCard(item = item)
                    }

                    // Loading more indicator
                    if (state.isLoadingMore) {
                        item {
                            LoadingMoreView()
                        }
                    }

                    // Error loading more
                    if (state.error != null && state.items.isNotEmpty()) {
                        item {
                            LoadMoreErrorView(
                                message = state.error,
                                onRetry = { onEvent(Event.Retry) }
                            )
                        }
                    }

                    // End of list
                    if (!state.hasMorePages && state.items.isNotEmpty() && !state.isLoadingMore) {
                        item {
                            EndOfListView()
                        }
                    }
                }
            }
        }
    }
}

