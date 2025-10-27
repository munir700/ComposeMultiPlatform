package kmp.core.mobile.testing

import kmp.core.mobile.base.ViewEvent
import kmp.core.mobile.base.ViewSideEffect
import kmp.core.mobile.base.ViewState

/**
 * Test utilities and builders for common test scenarios
 */
object TestDataBuilders {

    /**
     * Creates a list of items with default or custom values
     */
    fun <T> createItemList(
        count: Int = 10,
        builder: (index: Int) -> T
    ): List<T> = (0 until count).map { index -> builder(index) }

    /**
     * Simulates event handling with state mutation
     */
    fun <S : ViewState, E : ViewEvent> handleEvent(
        currentState: S,
        event: E,
        handler: (state: S, event: E) -> S
    ): S = handler(currentState, event)

    /**
     * Simulates multiple state transitions
     */
    fun <S : ViewState> simulateStateTransitions(
        initialState: S,
        transitions: List<(S) -> S>
    ): S = transitions.fold(initialState) { state, transition -> transition(state) }

    /**
     * Creates a test scenario with multiple steps
     */
    data class TestScenario<S : ViewState, E : ViewEvent>(
        val name: String,
        val initialState: S,
        val events: List<E>,
        val expectedFinalState: S
    )

    /**
     * Validates state transitions
     */
    fun <S : ViewState> validateStateTransition(
        initial: S,
        final: S,
        validator: (S, S) -> Boolean
    ): Boolean = validator(initial, final)
}

/**
 * Common test assertions for MVI components
 */
object MviAssertions {

    /**
     * Asserts that state is immutable after modification attempt
     */
    fun <S : ViewState> assertStateImmutable(
        original: S,
        modified: S,
        changeDescription: String
    ): Boolean {
        return original != modified
    }

    /**
     * Asserts that event produces expected state change
     */
    fun <S : ViewState, E : ViewEvent> assertEventStateChange(
        beforeState: S,
        afterState: S,
        event: E,
        expectedChange: (S, S) -> Boolean
    ): Boolean = expectedChange(beforeState, afterState)

    /**
     * Asserts that side effect is triggered
     */
    fun <SF : ViewSideEffect> assertSideEffectTriggered(
        effect: SF?,
        expectedType: Class<out ViewSideEffect>
    ): Boolean = effect != null && expectedType.isInstance(effect)
}

/**
 * Mock implementations for testing
 */
object TestMocks {

    /**
     * Creates a mock state that can be used for testing
     */
    data class MockState(
        val id: String = "mock-id",
        val timestamp: Long = System.currentTimeMillis(),
        val data: Map<String, Any> = emptyMap()
    ) : ViewState

    /**
     * Creates a mock event that can be used for testing
     */
    sealed class MockEvent : ViewEvent {
        object MockEventA : MockEvent()
        object MockEventB : MockEvent()
        data class MockEventWithData(val value: String) : MockEvent()
    }

    /**
     * Creates a mock side effect that can be used for testing
     */
    sealed class MockEffect : ViewSideEffect {
        object MockEffectA : MockEffect()
        data class MockEffectWithData(val message: String) : MockEffect()
    }
}

/**
 * Test data creation helpers
 */
object TestDataFactory {

    fun createString(
        prefix: String = "test",
        length: Int = 10
    ): String = "$prefix-$length"

    fun createId(
        prefix: String = "id",
        number: Int = 1
    ): String = "$prefix-$number"

    fun createMap(
        vararg pairs: Pair<String, Any>
    ): Map<String, Any> = mapOf(*pairs)

    fun createList(
        count: Int = 5,
        creator: (Int) -> String = { i -> "item-$i" }
    ): List<String> = (0 until count).map { creator(it) }
}

/**
 * Assertion helpers for common patterns
 */
object AssertionHelpers {

    /**
     * Asserts state list contains expected states
     */
    fun <S : ViewState> assertStatesContain(
        states: List<S>,
        predicate: (S) -> Boolean
    ): Boolean = states.any(predicate)

    /**
     * Asserts state list order
     */
    fun <S : ViewState, C : Comparable<C>> assertStatesOrdered(
        states: List<S>,
        extractor: (S) -> C
    ): Boolean {
        val extracted = states.map(extractor)
        return extracted == extracted.sorted()
    }

    /**
     * Asserts all states match condition
     */
    fun <S : ViewState> assertAllStatesSatisfy(
        states: List<S>,
        condition: (S) -> Boolean
    ): Boolean = states.all(condition)

    /**
     * Asserts state transition is valid
     */
    fun <S : ViewState> assertValidTransition(
        from: S,
        to: S,
        isValid: (S, S) -> Boolean
    ): Boolean = isValid(from, to)
}

/**
 * Performance testing utilities
 */
object PerformanceUtils {

    /**
     * Measures execution time of a block
     */
    fun measureTimeMillis(block: () -> Unit): Long {
        val start = System.currentTimeMillis()
        block()
        return System.currentTimeMillis() - start
    }

    /**
     * Asserts that block executes within time limit
     */
    fun assertExecutesWithinTimeLimit(
        timeoutMillis: Long,
        block: () -> Unit
    ): Boolean {
        val executionTime = measureTimeMillis(block)
        return executionTime <= timeoutMillis
    }

    /**
     * Runs block multiple times and measures average time
     */
    fun averageExecutionTime(
        iterations: Int = 100,
        block: () -> Unit
    ): Long {
        var totalTime = 0L
        repeat(iterations) {
            totalTime += measureTimeMillis(block)
        }
        return totalTime / iterations
    }
}

/**
 * Test result tracking
 */
object TestResultTracker {

    data class TestResult(
        val testName: String,
        val passed: Boolean,
        val executionTimeMs: Long,
        val errorMessage: String? = null
    )

    class ResultCollector {
        private val results = mutableListOf<TestResult>()

        fun addResult(result: TestResult) {
            results.add(result)
        }

        fun getResults(): List<TestResult> = results.toList()

        fun getPassedCount(): Int = results.count { it.passed }

        fun getFailedCount(): Int = results.count { !it.passed }

        fun getTotalExecutionTime(): Long = results.sumOf { it.executionTimeMs }

        fun getSummary(): String = """
            Test Results Summary
            ====================
            Total Tests: ${results.size}
            Passed: ${getPassedCount()}
            Failed: ${getFailedCount()}
            Total Time: ${getTotalExecutionTime()}ms
            Success Rate: ${(getPassedCount().toFloat() / results.size * 100).toInt()}%
        """.trimIndent()
    }
}

