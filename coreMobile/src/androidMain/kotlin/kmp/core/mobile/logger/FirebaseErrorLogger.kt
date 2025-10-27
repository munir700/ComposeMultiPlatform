package kmp.core.mobile.logger

import dev.gitlive.firebase.crashlytics.FirebaseCrashlytics

class FirebaseErrorLogger(
    private val crashlytics: FirebaseCrashlytics
) : IErrorLogger {

    override fun recordException(e: Throwable) {

        crashlytics.recordException(e)
    }

    override fun log(msg: String) {
        crashlytics.log(msg)
    }

    override fun setUserId(userId: String) {
        crashlytics.setUserId(userId)
    }

    override fun sendUnsentReports() {
        crashlytics.sendUnsentReports()
    }

    override fun deleteUnsentReports() {
        crashlytics.deleteUnsentReports()
    }

    override fun setCustomKey(key: String, value: String) {
        crashlytics.setCustomKey(key, value)
    }

    override fun setCustomKey(key: String, value: Boolean) {
        crashlytics.setCustomKey(key, value)
    }

    override fun setCustomKey(key: String, value: Double) {
        crashlytics.setCustomKey(key, value)
    }

    override fun setCustomKey(key: String, value: Float) {
        crashlytics.setCustomKey(key, value)
    }

    override fun setCustomKey(key: String, value: Int) {
        crashlytics.setCustomKey(key, value)
    }

    override fun setCustomKey(key: String, value: Long) {
        crashlytics.setCustomKey(key, value)
    }

    override fun setCustomKeys(customKeys: Map<String, Any>) {
        crashlytics.setCustomKeys(customKeys)
    }
}