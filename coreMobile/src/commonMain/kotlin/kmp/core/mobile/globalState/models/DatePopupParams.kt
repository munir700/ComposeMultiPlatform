package kmp.core.mobile.globalState.models

import kotlinx.datetime.LocalDate

data class DatePopupParams(
    val isCancellable: Boolean = true,
    val title: String? = null,
    val body: String? = null,
    val date: LocalDate? = null,
    val startDate: LocalDate? = null,
    val positiveButtonText: String? = null,
    val negativeButtonText: String? = null,
    val onDateSelected: ((LocalDate) -> Unit)? = null,
    val onDismiss: (() -> Unit)? = null
)