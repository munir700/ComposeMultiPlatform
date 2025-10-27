package kmp.core.mobile.language


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kmp.core.mobile.language.ILanguageManager.Companion.KEY_APP_LANG
import kmp.core.mobile.sharedPrefs.ISharedPrefsManager
import kmp.core.mobile.sharedPrefs.getObject
import kmp.core.mobile.sharedPrefs.save
import kmp.core.mobile.utils.extensions.getSystemLanguage
import platform.Foundation.NSUserDefaults
import platform.Foundation.setValue

class LanguageManager(
    private val prefsManager: ISharedPrefsManager
) : ILanguageManager {
    private var currentLanguage = mutableStateOf<Language?>(null)

    override fun getCurrentLanguage(): Language {
        // Load language if not loaded
        if (currentLanguage.value == null) {
            currentLanguage.value = prefsManager.getObject(KEY_APP_LANG) ?: getSystemLanguage()
        }

        // Return
        return currentLanguage.value!!
    }

    override fun changeLanguage(language: Language) {
        // Cache this language
        currentLanguage.value = language
        prefsManager.save(KEY_APP_LANG, language)

        // Change user defaults language
        NSUserDefaults.standardUserDefaults.run {
            setValue(listOf(language.code), forKey = "AppleLanguages")
            synchronize()
        }
    }

    override fun currentLanguageAsState(): State<Language?> {
        return currentLanguage
    }
}