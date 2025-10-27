package kmp.core.mobile.utils.extensions

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive


val json = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
    isLenient = true
}

inline fun <reified T> T.objectToMap(): Map<String, String> {
    // Convert object to JSON string
    val jsonString = json.encodeToString(this)
    // Parse JSON string to JsonObject
    val jsonObject = json.parseToJsonElement(jsonString).jsonObject

    // Map non-null values to a String map
    return jsonObject.mapNotNull { (key, value) ->
        value.jsonPrimitive.contentOrNull?.let { key to it }
    }.toMap()
}
