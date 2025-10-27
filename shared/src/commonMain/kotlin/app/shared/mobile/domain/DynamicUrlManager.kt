package app.shared.mobile.domain

import app.shared.mobile.domain.config.VersionResponse
import kmp.core.mobile.sharedPrefs.ISharedPrefsManager


interface DynamicUrlManager {
    suspend fun populateUrls(versionResponse: VersionResponse)
    suspend fun getServerUrl(): String?
    suspend fun getApiServerUrl(): String?
    suspend fun clearUrls()
    fun getFallbackSsoUrl(): String
    fun getFallbackApiUrl(): String
}

class DynamicUrlManagerImpl(
    private val sharedPrefs: ISharedPrefsManager
) : DynamicUrlManager {
    
    companion object {
        // Link names from iOS implementation
        private const val TYPE_1 = "TYPE_1"
        private const val TYPE_2 = "TYPE_2"
        
        // SharedPrefs keys
        private const val KEY_SERVER_URL = "server_url"
        private const val KEY_API_SERVER_URL = "api_server_url"
        
        // Fallback URLs
        private const val FALLBACK_URL = "server_url"
        private const val FALLBACK_API_URL = "api_server_url"
    }
    
    override suspend fun populateUrls(versionResponse: VersionResponse) {
        for (link in versionResponse.links) {
            when (link.name) {
                TYPE_1 -> {
                    val url = ensureTrailingSlash(link.value)
                    sharedPrefs.save(KEY_SERVER_URL, url)
                }
                TYPE_2 -> {
                    val url = ensureTrailingSlash(link.value)
                    sharedPrefs.save(KEY_API_SERVER_URL, url)
                }
            }
        }
    }
    
    override suspend fun getServerUrl(): String? {
        return sharedPrefs.getString(KEY_SERVER_URL)
    }
    
    override suspend fun getApiServerUrl(): String? {
        return sharedPrefs.getString(KEY_API_SERVER_URL)
    }
    
    override suspend fun clearUrls() {
        sharedPrefs.delete(KEY_SERVER_URL)
        sharedPrefs.delete(KEY_API_SERVER_URL)
    }
    
    override fun getFallbackSsoUrl(): String = FALLBACK_URL
    
    override fun getFallbackApiUrl(): String = FALLBACK_API_URL
    
    private fun ensureTrailingSlash(url: String): String {
        return if (url.endsWith("/")) url else "$url/"
    }
}