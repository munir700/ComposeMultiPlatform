package kmp.core.mobile.di

import org.koin.core.qualifier.named

object CoreDiQualifiers {
    val SHARED_PREFS_SECURED = named("shared_prefs_secured")
    val SHARED_PREFS_NORMAL = named("shared_prefs_normal")
    val BASIC_HTTP_CLIENT = named("basic_http_client")
}