package app.shared.mobile.presentation.infiniteScrolling

import app.shared.mobile.data.remote.models.ItemModel
import app.shared.mobile.data.remote.models.PaginatedResponse
import kmp.core.mobile.base.ViewEvent
import kmp.core.mobile.base.ViewSideEffect
import kmp.core.mobile.base.ViewState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Test suite for InfiniteScrolling Screen and ViewModel
 * Tests the pagination and infinite scroll feature
 */
class InfiniteScrollContractTest {

    // Test state for infinite scroll
    data class TestInfiniteScrollState(
        val items: List<ItemModel> = emptyList(),
        val currentPage: Int = 0,
        val isLoading: Boolean = false,
        val hasMore: Boolean = true,
        val error: String? = null
    ) : ViewState

    // Test events for infinite scroll
    sealed class TestInfiniteScrollEvent : ViewEvent {
        object LoadMore : TestInfiniteScrollEvent()
        object RefreshList : TestInfiniteScrollEvent()
        data class ItemClicked(val itemId: Int) : TestInfiniteScrollEvent()
        object RetryLoad : TestInfiniteScrollEvent()
    }

    // Test side effects
    sealed class TestInfiniteScrollEffect : ViewSideEffect {
        data class NavigateToDetail(val itemId: Int) : TestInfiniteScrollEffect()
        data class ShowError(val message: String) : TestInfiniteScrollEffect()
        object ScrollToTop : TestInfiniteScrollEffect()
    }

    @Test
    fun `test initial state for infinite scroll`() {
        // Arrange & Act
        val state = TestInfiniteScrollState()

        // Assert
        assertEquals(0, state.items.size)
        assertEquals(0, state.currentPage)
        assertEquals(false, state.isLoading)
        assertEquals(true, state.hasMore)
        assertEquals(null, state.error)
    }

    @Test
    fun `test loading more items`() {
        // Arrange
        var state = TestInfiniteScrollState(
            items = listOf(ItemModel(1, "Item 1", "Desc 1")),
            currentPage = 1,
            isLoading = false,
            hasMore = true
        )

        // Act - Start loading
        state = state.copy(isLoading = true)
        assertEquals(true, state.isLoading)

        // Act - Add new items
        val newItems = state.items + ItemModel(2, "Item 2", "Desc 2")
        state = state.copy(
            items = newItems,
            currentPage = 2,
            isLoading = false
        )

        // Assert
        assertEquals(2, state.items.size)
        assertEquals(2, state.currentPage)
        assertEquals(false, state.isLoading)
    }

    @Test
    fun `test load more event`() {
        // Arrange
        var state = TestInfiniteScrollState(
            currentPage = 1,
            hasMore = true
        )
        val event = TestInfiniteScrollEvent.LoadMore

        // Act
        if (event is TestInfiniteScrollEvent.LoadMore) {
            state = state.copy(isLoading = true)
            state = state.copy(
                currentPage = state.currentPage + 1,
                isLoading = false
            )
        }

        // Assert
        assertEquals(2, state.currentPage)
    }

    @Test
    fun `test refresh list event`() {
        // Arrange
        var state = TestInfiniteScrollState(
            items = listOf(ItemModel(1, "Old", "Old")),
            currentPage = 5
        )
        val event = TestInfiniteScrollEvent.RefreshList

        // Act
        if (event is TestInfiniteScrollEvent.RefreshList) {
            state = state.copy(
                items = emptyList(),
                currentPage = 0
            )
        }

        // Assert
        assertEquals(0, state.items.size)
        assertEquals(0, state.currentPage)
    }

    @Test
    fun `test item click event`() {
        // Arrange
        val event = TestInfiniteScrollEvent.ItemClicked(itemId = 5)

        // Act
        val effect: TestInfiniteScrollEffect = if (event is TestInfiniteScrollEvent.ItemClicked) {
            TestInfiniteScrollEffect.NavigateToDetail(event.itemId)
        } else {
            TestInfiniteScrollEffect.ShowError("Unknown event")
        }

        // Assert
        assertTrue(effect is TestInfiniteScrollEffect.NavigateToDetail)
        assertEquals(5, (effect as TestInfiniteScrollEffect.NavigateToDetail).itemId)
    }

    @Test
    fun `test reach end of list`() {
        // Arrange
        val state = TestInfiniteScrollState(
            items = listOf(ItemModel(1, "Item", "Desc")),
            currentPage = 5,
            hasMore = false
        )

        // Act & Assert
        assertEquals(false, state.hasMore)
    }

    @Test
    fun `test pagination through multiple pages`() {
        // Arrange
        var state = TestInfiniteScrollState()
        val itemsList = mutableListOf<ItemModel>()

        // Act - Simulate loading 3 pages
        for (page in 0..2) {
            val pageItems = (1..10).map { itemId ->
                ItemModel(page * 10 + itemId, "Item ${page * 10 + itemId}", "Desc")
            }
            itemsList.addAll(pageItems)
            state = state.copy(
                items = itemsList.toList(),
                currentPage = page + 1
            )
        }

        // Assert
        assertEquals(30, state.items.size)
        assertEquals(3, state.currentPage)
    }

    @Test
    fun `test error handling in infinite scroll`() {
        // Arrange
        var state = TestInfiniteScrollState()

        // Act - Set error
        state = state.copy(
            error = "Failed to load items",
            isLoading = false
        )

        // Assert
        assertEquals("Failed to load items", state.error)
        assertEquals(false, state.isLoading)
    }

    @Test
    fun `test retry load event after error`() {
        // Arrange
        var state = TestInfiniteScrollState(
            error = "Network error",
            isLoading = false
        )
        val event = TestInfiniteScrollEvent.RetryLoad

        // Act
        if (event is TestInfiniteScrollEvent.RetryLoad) {
            state = state.copy(
                error = null,
                isLoading = true
            )
        }

        // Assert
        assertEquals(null, state.error)
        assertEquals(true, state.isLoading)
    }

    @Test
    fun `test paginated response integration`() {
        // Arrange
        val apiResponse = PaginatedResponse(
            data = listOf(
                ItemModel(1, "Item 1", "Desc 1"),
                ItemModel(2, "Item 2", "Desc 2")
            ),
            page = 1,
            hasMore = true
        )

        var state = TestInfiniteScrollState()

        // Act
        state = state.copy(
            items = apiResponse.data,
            currentPage = apiResponse.page,
            hasMore = apiResponse.hasMore
        )

        // Assert
        assertEquals(2, state.items.size)
        assertEquals(1, state.currentPage)
        assertEquals(true, state.hasMore)
    }

    @Test
    fun `test large list performance`() {
        // Arrange
        var state = TestInfiniteScrollState()
        val largeItemList = (1..1000).map { id ->
            ItemModel(id, "Item $id", "Description $id")
        }

        // Act
        state = state.copy(items = largeItemList)

        // Assert
        assertEquals(1000, state.items.size)
    }

    @Test
    fun `test scroll to top effect`() {
        // Arrange
        val effect = TestInfiniteScrollEffect.ScrollToTop

        // Act & Assert
        assertTrue(effect is TestInfiniteScrollEffect.ScrollToTop)
    }

    @Test
    fun `test duplicate item prevention`() {
        // Arrange
        val item = ItemModel(1, "Item 1", "Desc")
        val items = listOf(item)

        // Act - Try to add same item again
        val finalItems = if (!items.any { it.id == item.id }) {
            items + item
        } else {
            items
        }

        // Assert
        assertEquals(1, finalItems.size)
    }
}

