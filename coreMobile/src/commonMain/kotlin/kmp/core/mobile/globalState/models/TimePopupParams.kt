package kmp.core.mobile.globalState.models

import kotlinx.datetime.LocalTime

data class TimePopupParams(
    val isCancellable: Boolean = true,
    val title: String? = null,
    val body: String? = null,
    val time: LocalTime? = null,
    val positiveButtonText: String? = null,
    val negativeButtonText: String? = null,
    val onTimeSelected: ((LocalTime) -> Unit)? = null,
    val onDismiss: (() -> Unit)? = null
)