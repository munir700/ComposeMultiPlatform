# KMP TDD Testing Guide

## Overview

This document provides a comprehensive guide for Test-Driven Development (TDD) in the KMP (Kotlin Multiplatform) project. The project uses an MVI (Model-View-Intent) architecture with separate `coreModule` and `sharedModule`.

## Project Structure

```
CMPApp/
├── coreMobile/           # Core reusable functionality
│   └── src/commonTest/   # Core module tests
├── shared/               # App-specific functionality
│   └── src/commonTest/   # Shared module tests
```

## Testing Architecture

### MVI Pattern Components

1. **ViewState (S)** - Immutable state representation
2. **ViewEvent (E)** - User actions/intents
3. **ViewSideEffect (SF)** - One-time effects (navigation, toasts, etc.)

### CoreViewModel

The `CoreViewModel<S, E, SF>` is the base class for all view models:

```kotlin
abstract class CoreViewModel<S : ViewState, E : ViewEvent, SF : ViewSideEffect> {
    // State Management
    protected fun setState(reducer: S.() -> S)
    
    // Event Handling
    fun setEvent(event: E)
    abstract fun handleEvents(event: E): Any
    
    // Side Effects
    protected fun setEffect(builder: () -> SF)
}
```

## Test Files Created

### CoreModule Tests

#### 1. **CoreViewModelTest.kt**
Tests the MVI architecture implementation:
- Initial state setup
- State mutations with reducers
- Event handling
- Side effect creation
- State immutability
- Multiple state transitions

**Key Test Cases:**
```kotlin
@Test
fun `test initial state is set correctly`()

@Test
fun `test state can be updated with reducer`()

@Test
fun `test multiple state updates work correctly`()

@Test
fun `test state immutability`()
```

#### 2. **DeepLinkTest.kt**
Tests deep link parsing and management:
- Deep link creation
- Custom deep link extensions
- Parser implementation
- URL parsing
- Deep link type differentiation
- Error handling

**Key Test Cases:**
```kotlin
@Test
fun `test parser parses user deeplink correctly`()

@Test
fun `test parser returns null for unknown deeplink`()

@Test
fun `test parser differentiates between link types`()
```

#### 3. **NetworkErrorTest.kt**
Tests error handling:
- Network error creation
- HTTP status codes
- Error body parsing
- Error type categorization
- Error response handling

**Key Test Cases:**
```kotlin
@Test
fun `test network response error creation with all parameters`()

@Test
fun `test common http error codes`()

@Test
fun `test error type categorization`()
```

### SharedModule Tests

#### 1. **AppBaseViewModelTest.kt**
Tests app-level view model functionality:
- User authentication state
- Login/logout flows
- Theme management
- Global state integration
- Navigation effects

**Key Test Cases:**
```kotlin
@Test
fun `test app state with user authentication`()

@Test
fun `test app state logout flow`()

@Test
fun `test theme change event`()
```

#### 2. **ModelsTest.kt**
Tests data models:

**ItemModelTest:**
- Model creation
- Equality comparison
- Copy functionality
- Data immutability
- Collection handling

**PaginatedResponseTest:**
- Paginated data creation
- Pagination flow
- Last page detection
- Generic type handling
- Data collection

#### 3. **InfiniteScrollTest.kt**
Tests infinite scrolling feature:
- Pagination state management
- Load more functionality
- List refresh
- Error handling and retry
- Performance with large lists

## TDD Workflow

### Step 1: Write the Test First

```kotlin
@Test
fun `test counter increments correctly`() {
    // Arrange
    var state = CounterState(count = 0)
    val event = CounterEvent.Increment
    
    // Act
    when (event) {
        is CounterEvent.Increment -> state = state.copy(count = state.count + 1)
    }
    
    // Assert
    assertEquals(1, state.count)
}
```

### Step 2: Run the Test (Red Phase)
- Test will fail initially
- This is expected!

### Step 3: Implement the Feature (Green Phase)
```kotlin
sealed class CounterEvent : ViewEvent {
    object Increment : CounterEvent()
}

data class CounterState(val count: Int) : ViewState
```

### Step 4: Refactor (Blue Phase)
- Improve code quality
- Remove duplication
- Ensure tests still pass

## Test Template

Use this template for your tests:

```kotlin
package your.package.path

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertNull

/**
 * Test suite for YourComponent
 * Tests description of what the component does
 */
class YourComponentTest {

    @Test
    fun `test descriptive test name matching behavior`() {
        // Arrange - Set up test data
        val testData = "Setup"
        
        // Act - Execute the code being tested
        val result = performAction(testData)
        
        // Assert - Verify the outcome
        assertEquals(expected, result)
    }

    @Test
    fun `test another specific behavior`() {
        // Arrange
        
        // Act
        
        // Assert
    }
}
```

## Running Tests

### Run All Tests
```bash
./gradlew test
```

### Run Specific Module Tests
```bash
# Core module tests
./gradlew coreMobile:commonTest

# Shared module tests
./gradlew shared:commonTest
```

### Run Specific Test Class
```bash
./gradlew test --tests "*CoreViewModelTest*"
```

### Run with Coverage
```bash
./gradlew test --info
```

## Best Practices

### 1. **Naming Conventions**
- Use descriptive test names following pattern: `test[What][When][Then]`
- Examples:
  ```kotlin
  @Test
  fun `test state updates when increment event is received`()
  
  @Test
  fun `test error is shown when network request fails`()
  ```

### 2. **Arrange-Act-Assert (AAA) Pattern**
- **Arrange**: Set up test data and preconditions
- **Act**: Execute the code being tested
- **Assert**: Verify the results

### 3. **Single Responsibility**
- Each test should verify one behavior
- Don't test multiple things in one test

### 4. **Test Independence**
- Tests should not depend on each other
- Each test should be able to run in any order

### 5. **Use Proper Assertions**
```kotlin
// Good
assertEquals(expected, actual)
assertTrue(condition)
assertNull(value)
assertFalse(condition)

// Avoid
assertTrue(expected == actual)
```

### 6. **State Immutability**
```kotlin
@Test
fun `test state immutability`() {
    val original = TestState(count = 10)
    val modified = original.copy(count = 20)
    
    // Original should not change
    assertEquals(10, original.count)
    assertEquals(20, modified.count)
}
```

### 7. **Test Data Builders**
```kotlin
fun createTestItem(
    id: Int = 1,
    title: String = "Default Title",
    description: String = "Default Description"
) = ItemModel(id, title, description)
```

## Common Test Patterns

### Testing State Transitions
```kotlin
@Test
fun `test state transitions for loading scenario`() {
    var state = TestState(isLoading = false)
    
    // Start loading
    state = state.copy(isLoading = true)
    assertEquals(true, state.isLoading)
    
    // Complete loading
    state = state.copy(isLoading = false)
    assertEquals(false, state.isLoading)
}
```

### Testing Event Handling
```kotlin
@Test
fun `test event updates state correctly`() {
    var state = TestState(count = 0)
    val event = TestEvent.Increment
    
    when (event) {
        is TestEvent.Increment -> state = state.copy(count = state.count + 1)
    }
    
    assertEquals(1, state.count)
}
```

### Testing Side Effects
```kotlin
@Test
fun `test side effect is created for navigation`() {
    val effect = TestEffect.NavigateToHome
    
    assertTrue(effect is TestEffect.NavigateToHome)
}
```

### Testing Data Models
```kotlin
@Test
fun `test model equality`() {
    val model1 = ItemModel(1, "Title", "Description")
    val model2 = ItemModel(1, "Title", "Description")
    
    assertEquals(model1, model2)
}

@Test
fun `test model copy function`() {
    val original = ItemModel(1, "Original", "Original Desc")
    val modified = original.copy(title = "Modified")
    
    assertEquals("Original", original.title)
    assertEquals("Modified", modified.title)
}
```

### Testing Collections
```kotlin
@Test
fun `test multiple items in collection`() {
    val items = (1..10).map { id ->
        ItemModel(id, "Title $id", "Description $id")
    }
    
    assertEquals(10, items.size)
}
```

### Testing Error Scenarios
```kotlin
@Test
fun `test parser returns null for invalid input`() {
    val parser = TestParser()
    val result = parser.parse("invalid-url")
    
    assertNull(result)
}
```

## Testing the MVI Components

### Testing ViewState
- Test immutability
- Test state transitions
- Test complex state changes
- Test data consistency

### Testing ViewEvent
- Test event creation
- Test event properties
- Test sealed class hierarchy
- Test event matching

### Testing ViewSideEffect
- Test effect creation
- Test effect types
- Test effect properties
- Test effect handling

## Integration with CI/CD

Add to your CI pipeline:

```yaml
test:
  stage: test
  script:
    - ./gradlew commonTest
  coverage: '/^Total coverage: \d+\.\d+\%/'
```

## Debugging Tests

### Enable Debug Logging
```kotlin
@Test
fun `test with logging`() {
    println("Starting test")
    // Your test code
    println("Test completed")
}
```

### Use IDE Test Runner
- In Android Studio/IntelliJ, right-click test and select "Run"
- Use debug mode to step through tests

## Performance Considerations

- Keep tests fast (< 100ms for unit tests)
- Avoid heavy operations in tests
- Use lightweight test data
- Mock external dependencies

## Continuous Improvement

1. **Increase Test Coverage**
   - Aim for 80%+ code coverage
   - Prioritize critical paths
   - Test edge cases

2. **Refactor Tests**
   - Remove duplication
   - Extract common setup
   - Use test fixtures

3. **Monitor Test Health**
   - Fix flaky tests immediately
   - Review failing tests
   - Optimize slow tests

## Additional Resources

- [Kotlin Testing Documentation](https://kotlinlang.org/docs/testing.html)
- [MVI Pattern Guide](https://hannesdorfmann.com/mosby3/mvi/)
- [TDD Best Practices](https://martinfowler.com/bliki/TestDrivenDevelopment.html)

---

**Last Updated:** 2024
**Test Suite Version:** 1.0

