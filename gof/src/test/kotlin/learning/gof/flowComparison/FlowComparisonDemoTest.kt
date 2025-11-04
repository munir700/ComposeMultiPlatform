package learning.gof.flowComparison

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class FlowComparisonDemoTest {
    @Test
    fun testTransformAddsTimesTen() = runBlocking {
        val result = mutableListOf<Int>()
        flowOf(1, 2, 3).transform { value ->
            emit(value)    // original
            emit(value * 10) // transformed
        }.collect { result.add(it) }
        println("Result: $result")

        //assertEquals(listOf(10, 20, 30), result)
        assertEquals(listOf(1, 10, 2, 20, 3, 30), result)
    }

    @Test
    fun testConditionalTransformEvenOnly() = runBlocking {
        val result = mutableListOf<Int>()
        flowOf(1, 2, 3, 4).transform { value ->
            if (value % 2 == 0) emit(value * 10)
        }.collect { result.add(it) }
        println("Result: $result")

        assertEquals(listOf(20, 40), result)
    }
}

