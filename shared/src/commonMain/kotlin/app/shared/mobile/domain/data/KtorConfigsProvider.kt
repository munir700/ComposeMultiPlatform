package app.shared.mobile.domain.data


import app.shared.mobile.constants.AppEnvironment
import app.shared.mobile.domain.DynamicUrlManager
import kmp.core.mobile.network.KtorConfigs
import kmp.core.mobile.remoteConfigs.IRemoteConfigs
import kmp.core.mobile.sharedPrefs.ISharedPrefsManager
import kmp.core.mobile.utils.extensions.getValidKtorUrl
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.Volatile

class KtorConfigsProvider(
    private val securedSharedPrefs: ISharedPrefsManager,
    private val remoteConfigs: IRemoteConfigs,
    private val environment: AppEnvironment,
    private val dynamicUrlManager: DynamicUrlManager
) {
    // Cache to avoid repeated runBlocking calls
    @Volatile
    private var cachedAuthUrl: String? = null
    
    fun getMockServerConfigs(): KtorConfigs {
        return KtorConfigs(
            baseUrl = environment.mockServerUrl.getValidKtorUrl()
        )
    }
    
    fun getAuthServerConfigs(): KtorConfigs {
        // Use cached URL if available, otherwise fetch once
        val baseUrl = cachedAuthUrl ?: run {
            val dynamicUrl = runBlocking { dynamicUrlManager.getServerUrl() }
            val url = dynamicUrl ?: dynamicUrlManager.getFallbackSsoUrl()
            cachedAuthUrl = url
            url
        }
        
        return KtorConfigs(
            baseUrl = baseUrl.getValidKtorUrl()
        )
    }
    
    /**
     * Clear cached URL to force refresh on next call
     * Should be called when configuration is updated
     */
    fun clearCache() {
        cachedAuthUrl = null
    }
}