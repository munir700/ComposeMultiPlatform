package kmp.core.mobile.navigations

import kotlinx.serialization.Serializable

typealias ScreenKey = String

@Serializable
data class ScreenConfig(
    val key: String,
    val data: String? = null
)