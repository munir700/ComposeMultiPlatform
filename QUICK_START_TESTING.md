# TDD Quick Start Guide

## Getting Started with Tests

### 1. Test Structure

All tests follow the **AAA Pattern** (Arrange-Act-Assert):

```kotlin
@Test
fun `test descriptive behavior`() {
    // Arrange - Setup
    val input = "data"
    
    // Act - Execute
    val result = functionUnderTest(input)
    
    // Assert - Verify
    assertEquals(expected, result)
}
```

### 2. Running Tests

```bash
# All tests
./gradlew test

# Core module only
./gradlew coreMobile:commonTest

# Shared module only
./gradlew shared:commonTest

# Specific test class
./gradlew test --tests "*CoreViewModelTest*"
```

## Common Test Patterns

### Testing State Management

```kotlin
@Test
fun `test state updates correctly`() {
    // Arrange
    var state = MyState(count = 0)
    
    // Act
    state = state.copy(count = 5)
    
    // Assert
    assertEquals(5, state.count)
}
```

### Testing Events

```kotlin
@Test
fun `test event handling`() {
    // Arrange
    var state = MyState(count = 0)
    val event = MyEvent.Increment
    
    // Act
    when (event) {
        is MyEvent.Increment -> state = state.copy(count = state.count + 1)
    }
    
    // Assert
    assertEquals(1, state.count)
}
```

### Testing Effects

```kotlin
@Test
fun `test side effect creation`() {
    // Arrange & Act
    val effect = MyEffect.ShowToast("Success")
    
    // Assert
    assertTrue(effect is MyEffect.ShowToast)
    assertEquals("Success", (effect as MyEffect.ShowToast).message)
}
```

### Testing Data Models

```kotlin
@Test
fun `test model equality`() {
    // Arrange
    val model1 = ItemModel(1, "Title", "Desc")
    val model2 = ItemModel(1, "Title", "Desc")
    
    // Assert
    assertEquals(model1, model2)
}

@Test
fun `test model copy`() {
    // Arrange
    val original = ItemModel(1, "Original", "Desc")
    
    // Act
    val modified = original.copy(title = "Modified")
    
    // Assert
    assertEquals("Original", original.title)
    assertEquals("Modified", modified.title)
}
```

## Test File Organization

### CoreModule Tests
```
coreMobile/src/commonTest/kotlin/kmp/core/mobile/
├── base/
│   └── CoreViewModelTest.kt
├── deepLink/
│   └── DeepLinkTest.kt
├── network/
│   └── errors/
│       └── NetworkErrorTest.kt
└── testing/
    └── TestUtils.kt
```

### SharedModule Tests
```
shared/src/commonTest/kotlin/app/shared/mobile/
├── presentation/
│   ├── base/
│   │   └── AppBaseViewModelTest.kt
│   └── infiniteScrolling/
│       └── InfiniteScrollTest.kt
├── data/
│   └── remote/
│       └── models/
│           └── ModelsTest.kt
```

## Assertion Methods

### Common Assertions

```kotlin
// Equality
assertEquals(expected, actual)

// Boolean
assertTrue(condition)
assertFalse(condition)

// Null checks
assertNull(value)
assertNotNull(value)

// Collections
assertEquals(5, list.size)
assertTrue(list.contains(item))
```

## Testing Tips

### 1. **Use Descriptive Names**
```kotlin
// Good ✓
@Test
fun `test counter increments when increment event is received`()

// Bad ✗
@Test
fun test1()
```

### 2. **Test One Thing**
```kotlin
// Good ✓
@Test
fun `test state increments on increment event`()

@Test
fun `test state resets on reset event`()

// Bad ✗
@Test
fun `test all events work`() // Tests multiple things
```

### 3. **Arrange-Act-Assert**
```kotlin
@Test
fun `test feature works`() {
    // Arrange
    val setup = prepareTestData()
    
    // Act
    val result = executeFeature(setup)
    
    // Assert
    verify(result)
}
```

### 4. **Don't Test Implementation Details**
```kotlin
// Good ✓
@Test
fun `test correct value is returned`() {
    assertEquals(expected, getValue())
}

// Bad ✗
@Test
fun `test getValue() is called exactly once`() {
    // Testing internals, not behavior
}
```

### 5. **Use Test Fixtures**
```kotlin
class MyTest {
    private val testData = ItemModel(1, "Test", "Description")
    
    @Test
    fun `test uses fixture`() {
        assertEquals(1, testData.id)
    }
}
```

## Test Utilities Available

### Use TestDataBuilders
```kotlin
import kmp.core.mobile.testing.TestDataBuilders

// Create list of items
val items = TestDataBuilders.createItemList(10) { index ->
    ItemModel(index, "Title $index", "Desc")
}

// Handle events
val newState = TestDataBuilders.handleEvent(
    currentState = state,
    event = event,
    handler = { s, e -> /* handle */ s }
)
```

### Use Test Mocks
```kotlin
import kmp.core.mobile.testing.TestMocks

val mockState = TestMocks.MockState(
    id = "test-id",
    data = mapOf("key" to "value")
)

val mockEvent = TestMocks.MockEvent.MockEventA
val mockEffect = TestMocks.MockEffect.MockEffectA
```

### Use Test Factory
```kotlin
import kmp.core.mobile.testing.TestDataFactory

val testString = TestDataFactory.createString("test", 20)
val testId = TestDataFactory.createId("item", 1)
val testMap = TestDataFactory.createMap("key" to "value")
val testList = TestDataFactory.createList(5)
```

## Best Practices Checklist

- [ ] Tests have descriptive names
- [ ] Each test tests one behavior
- [ ] Tests follow AAA pattern
- [ ] No test depends on another test
- [ ] Tests are fast (< 100ms each)
- [ ] No hardcoded values in tests (use variables)
- [ ] Related tests grouped in same class
- [ ] Use appropriate assertions
- [ ] Mock external dependencies
- [ ] Clear test failure messages

## Troubleshooting

### Test Fails Unexpectedly
```kotlin
// Add debugging
@Test
fun `test with debug`() {
    println("Initial state: $state")
    // ... test code ...
    println("Final state: $state")
}
```

### State Not Updating
```kotlin
// Remember: states are immutable
var state = MyState() // Don't forget 'var'
state = state.copy(field = newValue) // Must reassign
```

### Event Not Handling
```kotlin
// Ensure event is handled
when (event) {
    is MyEvent.Type1 -> state = state.copy(...)
    is MyEvent.Type2 -> state = state.copy(...)
    // Make sure all cases are covered
}
```

## Next Steps

1. **Read** the full [TESTING_GUIDE.md](./TESTING_GUIDE.md)
2. **Review** existing test files for patterns
3. **Write** tests before implementing features
4. **Run** tests frequently during development
5. **Refactor** both code and tests

## Test Files Reference

| Module | File | Purpose |
|--------|------|---------|
| Core | CoreViewModelTest.kt | MVI architecture |
| Core | DeepLinkTest.kt | Deep link parsing |
| Core | NetworkErrorTest.kt | Error handling |
| Core | TestUtils.kt | Test utilities |
| Shared | AppBaseViewModelTest.kt | App view models |
| Shared | ModelsTest.kt | Data models |
| Shared | InfiniteScrollTest.kt | Pagination |

---

Happy Testing! 🚀

