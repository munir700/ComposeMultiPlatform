package app.shared.mobile.domain.repositories


import kmp.core.mobile.base.CoreRepository
import kmp.core.mobile.di.CoreDiQualifiers
import kmp.core.mobile.sharedPrefs.ISharedPrefsManager
import org.koin.core.component.inject
import kotlin.getValue

open class BaseRepository : CoreRepository() {
    protected val normalSharedPrefs by inject<ISharedPrefsManager>(CoreDiQualifiers.SHARED_PREFS_NORMAL)
    protected val securedSharedPrefs by inject<ISharedPrefsManager>(CoreDiQualifiers.SHARED_PREFS_SECURED)
}