package kmp.core.mobile.logger

interface IErrorLogger {
    fun recordException(e: Throwable)
    fun log(msg: String)
    fun setUserId(userId: String)
    fun sendUnsentReports()
    fun deleteUnsentReports()
    fun setCustomKey(key: String, value: String)
    fun setCustomKey(key: String, value: Boolean)
    fun setCustomKey(key: String, value: Double)
    fun setCustomKey(key: String, value: Float)
    fun setCustomKey(key: String, value: Int)
    fun setCustomKey(key: String, value: Long)
    fun setCustomKeys(customKeys: Map<String, Any>)
}