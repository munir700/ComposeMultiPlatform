package kmp.core.mobile.globalState.models

data class DateRangePopupParams(
    val isCancellable: Boolean = true,
    val title: String? = null,
    val body: String? = null,
    val fromDate: Long? = null,
    val toDate: Long? = null,
    val positiveButtonText: String? = null,
    val negativeButtonText: String? = null,
    val onDateRangeSelected: ((Long?, Long?) -> Unit)? = null,
    val onDismiss: (() -> Unit)? = null
)