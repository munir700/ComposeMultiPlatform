package kmp.core.mobile.deepLink

interface DeepLinkParser {
    fun parse(url: String): DeepLink?
}