package kmp.core.mobile.deepLink


import io.ktor.http.Url
import kmp.core.mobile.AppLogger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest

internal class DeepLinkManager(
    private val appLogger: AppLogger,
    private val parsers: Map<String, DeepLinkParser>
) : IDeepLinkManager {
    private val deepLinksFlow = MutableSharedFlow<DeepLink>(replay = 1)

    override fun emitDeepLink(link: String) {
        validAndParseDeepLink(link)
    }

    private fun validAndParseDeepLink(url: String) {
        try {

            val parsedUrl = Url(url)

            // Extract path and parse the deep link
            val host = parsedUrl.host
            val deepLinkParser = parsers[host] ?: return
            val deepLink = deepLinkParser.parse(url)

            // Emit it if possible
            if (deepLink != null) {
                deepLinksFlow.tryEmit(deepLink)
            }
        } catch (e: Exception) {
            appLogger.log("Failed to parse deep link: $url - Error: ${e.message}")
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun observeDeepLinks(callback: (DeepLink) -> Unit) {
        deepLinksFlow.collectLatest {
            callback.invoke(it)
            deepLinksFlow.resetReplayCache()
        }
    }
}