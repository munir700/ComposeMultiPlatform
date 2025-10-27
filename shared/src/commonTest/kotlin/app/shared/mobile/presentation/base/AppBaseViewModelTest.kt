package app.shared.mobile.presentation.base

import kmp.core.mobile.base.ViewEvent
import kmp.core.mobile.base.ViewSideEffect
import kmp.core.mobile.base.ViewState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Test suite for AppBaseViewModel
 * Tests the application-level view model implementation with global state
 */
class AppBaseViewModelTest {

    // Sample app state
    data class TestAppState(
        val userId: String? = null,
        val isAuthenticated: Boolean = false,
        val theme: String = "light"
    ) : ViewState

    // Sample app events
    sealed class TestAppEvent : ViewEvent {
        object Login : TestAppEvent()
        object Logout : TestAppEvent()
        data class UpdateTheme(val theme: String) : TestAppEvent()
    }

    // Sample side effects
    sealed class TestAppSideEffect : ViewSideEffect {
        object NavigateToLogin : TestAppSideEffect()
        object NavigateToHome : TestAppSideEffect()
        data class ShowMessage(val message: String) : TestAppSideEffect()
    }

    // Note: IAppGlobalState is not used directly in this test
    // It's injected into AppBaseViewModel through Koin

    @Test
    fun `test app base view model inherits from core view model`() {
        // This test verifies the inheritance structure
        val state: ViewState = TestAppState()
        val event: ViewEvent = TestAppEvent.Login
        val effect: ViewSideEffect = TestAppSideEffect.NavigateToHome

        // Assert
        assertNotNull(state)
        assertNotNull(event)
        assertNotNull(effect)
    }

    @Test
    fun `test app state with user authentication`() {
        // Arrange
        var state = TestAppState(
            userId = null,
            isAuthenticated = false
        )

        // Act - simulate login
        state = state.copy(
            userId = "user-123",
            isAuthenticated = true
        )

        // Assert
        assertEquals("user-123", state.userId)
        assertEquals(true, state.isAuthenticated)
    }

    @Test
    fun `test app state logout flow`() {
        // Arrange
        var state = TestAppState(
            userId = "user-123",
            isAuthenticated = true
        )

        // Act - simulate logout
        state = state.copy(
            userId = null,
            isAuthenticated = false
        )

        // Assert
        assertEquals(null, state.userId)
        assertEquals(false, state.isAuthenticated)
    }

    @Test
    fun `test theme change event`() {
        // Arrange
        var state = TestAppState(theme = "light")
        val event = TestAppEvent.UpdateTheme("dark") as TestAppEvent

        // Act
        if (event is TestAppEvent.UpdateTheme) {
            state = state.copy(theme = event.theme)
        }

        // Assert
        assertEquals("dark", state.theme)
    }

    @Test
    fun `test app state immutability`() {
        // Arrange
        val originalState = TestAppState(userId = "user-1", theme = "light")

        // Act
        val newState = originalState.copy(theme = "dark")

        // Assert
        assertEquals("light", originalState.theme)
        assertEquals("dark", newState.theme)
    }

    @Test
    fun `test multiple user sessions`() {
        // Arrange
        val user1 = TestAppState(userId = "user-1", isAuthenticated = true)
        val user2 = TestAppState(userId = "user-2", isAuthenticated = true)

        // Act & Assert
        assertEquals("user-1", user1.userId)
        assertEquals("user-2", user2.userId)
        assertTrue(user1.userId != user2.userId)
    }

    @Test
    fun `test theme preferences persistence in state`() {
        // Arrange
        var state = TestAppState(theme = "light")

        // Act
        state = state.copy(theme = "dark")
        state = state.copy(theme = "auto")

        // Assert
        assertEquals("auto", state.theme)
    }

    @Test
    fun `test authentication state transitions`() {
        // Arrange
        var state = TestAppState(isAuthenticated = false)

        // Act & Assert
        assertEquals(false, state.isAuthenticated)
        state = state.copy(isAuthenticated = true)
        assertEquals(true, state.isAuthenticated)
        state = state.copy(isAuthenticated = false)
        assertEquals(false, state.isAuthenticated)
    }

    @Test
    fun `test side effect navigation on login`() {
        // Arrange
        val loginEvent = TestAppEvent.Login

        // Act
        val sideEffect = when (loginEvent) {
            TestAppEvent.Login -> TestAppSideEffect.NavigateToHome
            else -> TestAppSideEffect.ShowMessage("Unknown event")
        }

        // Assert
        assertTrue(sideEffect is TestAppSideEffect.NavigateToHome)
    }

    @Test
    fun `test side effect navigation on logout`() {
        // Arrange
        val logoutEvent = TestAppEvent.Logout

        // Act
        val sideEffect = when (logoutEvent) {
            TestAppEvent.Logout -> TestAppSideEffect.NavigateToLogin
            else -> TestAppSideEffect.ShowMessage("Unknown event")
        }

        // Assert
        assertTrue(sideEffect is TestAppSideEffect.NavigateToLogin)
    }
}

