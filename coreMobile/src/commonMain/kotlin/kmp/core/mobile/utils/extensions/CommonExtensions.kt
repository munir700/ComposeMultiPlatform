package kmp.core.mobile.utils.extensions


import kmp.core.mobile.PlatformType
import kmp.core.mobile.language.Language
import kotlinx.coroutines.CancellableContinuation
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

fun Boolean?.orFalse() = this ?: false

fun Boolean?.orTrue() = this ?: true

fun <T> CancellableContinuation<T>.resumeIfActive(value: T) {
    if (isActive) resume(value)
}

fun CancellableContinuation<*>.exceptionIfActive(throwable: Throwable) {
    if (isActive) resumeWithException(throwable)
}

fun CancellableContinuation<*>.exceptionIfActive(error: String?) {
    if (isActive) resumeWithException(Throwable(error))
}

inline fun <reified T> toMapOfString(obj: T): Map<String, String> {
    val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        explicitNulls = true
    }
    val jsonElement = json.encodeToJsonElement(obj)
    return jsonElement.jsonObject.toMapOfString()
}

fun JsonObject.toMapOfString(): Map<String, String> {
    return entries.associate { (key, jsonElement) ->
        key to (jsonElement.jsonPrimitive.contentOrNull ?: jsonElement.toString())
    }
}

fun <R> ifTrue(expression: Boolean, block: () -> R): R? {
    return if (expression) block() else null
}

fun CancellableContinuation<*>.cancelIfActive() {
    if (isActive) cancel()
}

fun <T> T.applyIf(condition: Boolean, block: T.() -> Unit): T {
    return apply {
        if (condition) block()
    }
}

expect fun randomUUID(): String

expect fun PlatformType.Companion.current(): PlatformType

expect fun getSystemLanguage(): Language

expect fun getOSVersion(): String