package kmp.core.mobile.utils.extensions

import androidx.compose.foundation.lazy.LazyListState
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement

fun <T> ArrayDeque<T>.removeUntilIndex(index: Int) {
    require(index >= 0) { "Index must be a non-negative integer" }
    while (size > index) {
        removeLast()
    }
}

fun <T> ArrayDeque<T>.removeLast(count: Int) {
    repeat(count) {
        removeLast()
    }
}

fun <T> List<T>.toArrayList(): ArrayList<T> {
    return ArrayList(this)
}

fun <K, V> Map<K, V>.toPairs(): List<Pair<K, V>> {
    return this.map { (key, value) -> key to value }
}

inline fun <reified V> Map<String, V>.toJsonObject(): JsonObject {
    val elements = this.map { (key, value) ->
        key to Json.encodeToJsonElement(value)
    }
    return JsonObject(elements.toMap())
}

fun <T> List<T>.secondOrNull(): T? {
    return if (isEmpty()) null else this[1]
}

fun <T> List<T>?.isNotNullOrEmpty(): Boolean {
    return !this.isNullOrEmpty()
}

fun LazyListState.isScrolledToEnd(): Boolean {
    val lastVisibleIndex = this.layoutInfo.visibleItemsInfo.lastOrNull()?.index
    val totalItemsCount = this.layoutInfo.totalItemsCount
    return lastVisibleIndex != null && lastVisibleIndex == totalItemsCount - 1
}

fun <T> List<T>.split(): Pair<List<T>, List<T>> {
    val midpoint = (this.size + 1) / 2
    return this.subList(0, midpoint) to this.subList(midpoint, this.size)
}

fun <T> List<T>.splitEdges(edgeSize: Int = this.size / 2): Pair<List<T>, List<T>> {
    val size = this.size / 2
    val finalEdgeSize = edgeSize.coerceAtMost(size)
    val left = this.take(finalEdgeSize)
    val right = this.takeLast(finalEdgeSize)
    return left to right
}