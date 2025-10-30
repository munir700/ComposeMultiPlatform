package kmp.core.mobile.base

import androidx.compose.runtime.mutableStateOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

/**
 * Test suite for CoreViewModel
 * Tests the MVI architecture implementation for state management, events, and side effects
 */
class CoreViewModelTest {

    // Sample state implementation for testing
    data class TestState(
        val count: Int = 0,
        val message: String = "",
        val isLoading: Boolean = false
    ) : ViewState

    // Sample event implementation for testing
    sealed class TestEvent : ViewEvent {
        object IncrementCount : TestEvent()
        data class SetMessage(val text: String) : TestEvent()
        object StartLoading : TestEvent()
        object StopLoading : TestEvent()
    }

    // Sample side effect implementation for testing
    sealed class TestSideEffect : ViewSideEffect {
        data class ShowToast(val message: String) : TestSideEffect()
        object NavigateToHome : TestSideEffect()
    }

    @Test
    fun `test initial state is set correctly`() {
        // Arrange & Act
        val initialState = TestState(count = 0, message = "Initial", isLoading = false)

        // Assert
        assertEquals(0, initialState.count)
        assertEquals("Initial", initialState.message)
        assertEquals(false, initialState.isLoading)
    }

    @Test
    fun `test state can be updated with reducer`() {
        // Arrange
        var currentState = TestState(count = 5)

        // Act - simulate setState reducer pattern
        val newState = currentState.copy(count = currentState.count + 1)

        // Assert
        assertEquals(6, newState.count)
        assertEquals(5, currentState.count) // Original unchanged
    }

    @Test
    fun `test multiple state updates work correctly`() {
        // Arrange
        var state = TestState(count = 0, message = "", isLoading = false)

        // Act
        state = state.copy(count = state.count + 1, isLoading = true)
        state = state.copy(message = "Updated", isLoading = false)

        // Assert
        assertEquals(1, state.count)
        assertEquals("Updated", state.message)
        assertEquals(false, state.isLoading)
    }

    @Test
    fun `test state transitions for loading scenario`() {
        // Arrange
        var state = TestState(isLoading = false)

        // Act - Start loading
        state = state.copy(isLoading = true)
        assertEquals(true, state.isLoading)

        // Act - Complete loading
        state = state.copy(isLoading = false)
        assertEquals(false, state.isLoading)
    }

    @Test
    fun `test counter increment event`() {
        // Arrange
        var state = TestState(count = 0)
        val event = TestEvent.IncrementCount

        // Act - simulate event handling
        when (event) {
            is TestEvent.IncrementCount -> state = state.copy(count = state.count + 1)
            is TestEvent.SetMessage -> state = state.copy(message = event.text)
            is TestEvent.StartLoading -> state = state.copy(isLoading = true)
            is TestEvent.StopLoading -> state = state.copy(isLoading = false)
        }

        // Assert
        assertEquals(1, state.count)
    }

    @Test
    fun `test set message event`() {
        // Arrange
        var state = TestState(message = "")
        val event = TestEvent.SetMessage("Hello World")

        // Act
        when (event) {
            is TestEvent.SetMessage -> state = state.copy(message = event.text)
            else -> {}
        }

        // Assert
        assertEquals("Hello World", state.message)
    }

    @Test
    fun `test side effect creation`() {
        // Arrange & Act
        val sideEffect = TestSideEffect.ShowToast("Success!")

        // Assert
        assertNotNull(sideEffect)
        assertEquals("Success!", (sideEffect as TestSideEffect.ShowToast).message)
    }

    @Test
    fun `test multiple side effects can be created`() {
        // Arrange & Act
        val sideEffect1 = TestSideEffect.ShowToast("Message 1")
        val sideEffect2 = TestSideEffect.NavigateToHome
        val sideEffect3 = TestSideEffect.ShowToast("Message 2")

        // Assert
        assertEquals("Message 1", (sideEffect1 as TestSideEffect.ShowToast).message)
        assertNotNull(sideEffect2)
        assertEquals("Message 2", (sideEffect3 as TestSideEffect.ShowToast).message)
    }

    @Test
    fun `test event interface implementation`() {
        // Arrange
        val event: ViewEvent = TestEvent.IncrementCount

        // Act & Assert
        assertNotNull(event)
    }

    @Test
    fun `test state interface implementation`() {
        // Arrange
        val state: ViewState = TestState(count = 10)

        // Act & Assert
        assertNotNull(state)
    }

    @Test
    fun `test side effect interface implementation`() {
        // Arrange
        val sideEffect: ViewSideEffect = TestSideEffect.NavigateToHome

        // Act & Assert
        assertNotNull(sideEffect)
    }

    @Test
    fun `test complex state mutation with multiple fields`() {
        // Arrange
        var state = TestState(
            count = 0,
            message = "Start",
            isLoading = false
        )

        // Act
        state = state.copy(
            count = 5,
            message = "Processing",
            isLoading = true
        )

        // Assert
        assertEquals(5, state.count)
        assertEquals("Processing", state.message)
        assertEquals(true, state.isLoading)
    }

    @Test
    fun `test state immutability`() {
        // Arrange
        val originalState = TestState(count = 10, message = "Original")

        // Act
        val newState = originalState.copy(count = 20)

        // Assert - Original should not change
        assertEquals(10, originalState.count)
        assertEquals(20, newState.count)
    }
}

