package kmp.core.mobile

open class AppLogger(
    private val isDebuggable: Boolean
) {
    fun log(msg: String) {
        if (isDebuggable.not()) return
        println(msg)
    }

    fun log(tag: String?, msg: String) {
        log("($tag) $msg")
    }
}